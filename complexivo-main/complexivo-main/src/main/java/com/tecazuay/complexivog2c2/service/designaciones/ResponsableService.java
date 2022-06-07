package com.tecazuay.complexivog2c2.service.designaciones;

import com.tecazuay.complexivog2c2.dto.carreras.CodigoCarrerasResponse;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.ResponsableRequest;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.ResponsableResponse;
import com.tecazuay.complexivog2c2.dto.email.EmailBody;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.repository.Primary.autoridades.AutoridadesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera.CoordinadorRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import com.tecazuay.complexivog2c2.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResponsableService implements Serializable {

    @Autowired
    private ResponsablePPPRepository responsablePPPRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CoordinadorRepository coordinadorRepository;
    @Autowired
    private PersonasRepository personasRepository;
    @Autowired
    private CoordinadorVinculacionRepository coordinadorVinculacionRepository;
    @Autowired
    private AutoridadesRepository autoridadesRepository;
    @Autowired
    private TutorAcademicoRepository tutorAcademicoRepository;

    /**
     * Método para designación de responsable de PP, valiamos que exista el docente según la cédula
     * En caso de que un docente se haya registrado antes de ser designado
     * al momento de ser designado se envia un correo electrónico
     * El email para enviar el correo se obtiene a través de la cédula
     *
     * @param DirectorProyectoRequest
     * @return
     */
    public boolean saveRolResposable(ResponsableRequest responsableRequest) {

        if (coordinadorRepository.existsByCedula(responsableRequest.getCedula())) {
            throw new BadRequestException("Ya se encuentra designado como Coordinador de Carrera");
        }
        if (coordinadorVinculacionRepository.existsByCedulaAndEstado(responsableRequest.getCedula(), true)) {
            throw new BadRequestException("Ya se encuentra designado como Coordinador de Vinculación");
        }
        if (autoridadesRepository.existsByCedulaAndEstado(responsableRequest.getCedula(), true)) {
            throw new BadRequestException("Ya se encuentra designado como Autoridad");
        }
        if (tutorAcademicoRepository.existsByCedulaAndEstado(responsableRequest.getCedula(), true)) {
            throw new BadRequestException("Ya se encuentra designado como Docente de Apoyo");
        }
        if (responsablePPPRepository.existsByEstadoAndCodigoCarrera(true, responsableRequest.getCodigoCarrera())) {
            throw new BadRequestException("Ya se encuentra designado como Responsable de Prácticas");
        }
        Optional<ResponsablePPP> optional = responsablePPPRepository.findByCedula(responsableRequest.getCedula());
        ResponsablePPP r;
        if (optional.isPresent()) {
            r = optional.get();
            r.setEstado(true);
            r.setFecha_inicio_periodo(responsableRequest.getFecha_inicio_periodo());
            r.setFecha_fin_periodo(responsableRequest.getFecha_fin_periodo());
            r.setFecha_designacion(responsableRequest.getFecha_designacion());

            responsablePPPRepository.save(r);
        } else {
            ResponsablePPP responsablePPP = new ResponsablePPP();
            CoordinadorCarrera cc = getCedula(responsableRequest.getCoordinador_id());
            if (personasRepository.existsByCedula(responsableRequest.getCedula())) {
                responsablePPP.setCedula(responsableRequest.getCedula());
                responsablePPP.setCoordinadorCarrera(cc);
                responsablePPP.setEstado(responsableRequest.isEstado());
                responsablePPP.setCodigoCarrera(responsableRequest.getCodigoCarrera());
                responsablePPP.setFecha_inicio_periodo(responsableRequest.getFecha_inicio_periodo());
                responsablePPP.setFecha_fin_periodo(responsableRequest.getFecha_fin_periodo());
                responsablePPP.setFecha_designacion(responsableRequest.getFecha_designacion());
                try {
                    r = responsablePPPRepository.save(responsablePPP);

                } catch (Exception e) {
                    throw new BadRequestException("No se guardó al responsable");
                }

            } else {
                log.error("No existe el responsable con cedula: {}", responsableRequest.getCedula());
                throw new ResponseNotFoundException("ResponsablePPP", "Cedula: ", "" + responsableRequest.getCedula());
            }
        }
        try {
            enviarCorreos(r);
            return true;
        } catch (Exception e) {
            throw new BadRequestException("No se envió el correo al responsable");
        }

    }

    /**
     * Metodo para validar que existe en la tabla usuario y enviar correo electrónico
     *
     * @param directorProyecto
     */
    public void enviarCorreos(ResponsablePPP responsablePPP) {
        Optional<Usuario> optional = usuarioRepository.findByCedula(responsablePPP.getCedula());
        if (optional.isPresent()) {
            EmailBody e = new EmailBody();
            e.setEmail(List.of(optional.get().getEmail()));
            e.setContent("Usted ha sido designado como Responsable de Prácticas Pre Profesionales");
            e.setText2(" Ingrese al sistema dando clic en el siguiente botón:");
            e.setSubject("Designación para proyectos de vinculación");
            emailService.sendEmail(e);
        }
    }

    /**
     * Obtener al Coordinador de Carerra segun cedula
     *
     * @param cedula
     * @return
     */
    public CoordinadorCarrera getCedula(String cedula) {
        Optional<CoordinadorCarrera> optional = coordinadorRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            return optional.get();
        }
        CoordinadorCarrera c = new CoordinadorCarrera();
        c.setCedula(cedula);
        return coordinadorRepository.save(c);
    }

    /**
     * Metodo para listar todos los responsables
     *
     * @return
     */
    public List<ResponsableResponse> lista() {
        return responsablePPPRepository.findAll().stream().map(responsablePPP -> {
            ResponsableResponse rp = new ResponsableResponse();
            rp.setId(responsablePPP.getId());
            rp.setCedula(responsablePPP.getCedula());
            rp.setEstado(responsablePPP.isEstado());
            rp.setCodigoCarrera(responsablePPP.getCodigoCarrera());
            rp.setFecha_inicio_periodo(responsablePPP.getFecha_inicio_periodo());
            rp.setFecha_fin_periodo(responsablePPP.getFecha_fin_periodo());
            rp.setFecha_designacion(responsablePPP.getFecha_designacion());
            rp.setCoordinador_id(responsablePPP.getCoordinadorCarrera().getId()+"");
            rp.setUsuario_id(responsablePPP.getUsuario().getId()+"");
            return rp;
        }).collect(Collectors.toList());

    }

    /**
     * Metodo para actualizar el estado del responsable
     *
     * @param responsableRequest
     * @return
     */
    public boolean updateResponsable(ResponsableRequest responsableRequest) {
        Optional<ResponsablePPP> exists = responsablePPPRepository.findById(responsableRequest.getId());
        if (exists.isPresent()) {
            ResponsablePPP r = exists.get();
            r.setEstado(responsableRequest.isEstado());
            r.setFecha_inicio_periodo(responsableRequest.getFecha_inicio_periodo());
            r.setFecha_fin_periodo(responsableRequest.getFecha_fin_periodo());
            r.setFecha_designacion(responsableRequest.getFecha_designacion());
            responsablePPPRepository.save(r);
            return true;
        }
        throw new ResponseNotFoundException("ResponsablePPP", "ID:", "" + responsableRequest.getId());
    }


    public CodigoCarrerasResponse codigoCarrera(String cedula) {
        Optional<ResponsablePPP> optional = responsablePPPRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            CodigoCarrerasResponse ca = new CodigoCarrerasResponse();
            ca.setCodigo(optional.get().getCodigoCarrera());

            return ca;
        } else throw new ResponseNotFoundException("ResponsablePPP", "cedula", cedula);
    }

}
