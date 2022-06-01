package com.tecazuay.complexivog2c2.service.designaciones;

import com.tecazuay.complexivog2c2.dto.docentes.DirectorCedulaResponse;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.TutorEmpresarialProyectoRequest;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.TutorEmpresarialProyectoResponse;
import com.tecazuay.complexivog2c2.dto.email.EmailBody;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmpresarial;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera.CoordinadorRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpresarialProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import com.tecazuay.complexivog2c2.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TutorEmpresarialService {

    @Autowired
    private TutorEmpresarialProyectoRepository tutorEmpresarialProyectoRepository;

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private PersonasRepository personasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProyectoRepository proyectoRepository;


    /**
     * Método para designación de director de proyecto, valiamos que exista el docente según la cédula
     * En caso de que un docente se haya registrado antes de ser designado
     * al momento de ser designado se envia un correo electrónico
     * El email para enviar el correo se obtiene a través de la cédula
     *
     * @param DirectorProyectoRequest
     * @return
     */
    public boolean saveRolDirector(TutorEmpresarialProyectoRequest tutorEmpresarialProyectoRequest) {
        if (tutorEmpresarialProyectoRepository.existsByCedulaAndEstado(tutorEmpresarialProyectoRequest.getCedula(), true)) {
            throw new BadRequestException("Ya está asignado como Director de un Proyecto");
        }
        Optional<ProyectoPPP> optional = proyectoRepository.findById(tutorEmpresarialProyectoRequest.getIdProyecto());
        if (optional.isPresent()) {
            if (!optional.get().isEstado())
                throw new BadRequestException("El proyecto ha finalizado, no es posible modificar sus datos");

            if (tutorEmpresarialProyectoRepository.existsByProyectoPPP(optional.get())) {
                throw new BadRequestException("Ya está asignado un Director de  Proyecto");
            }
        } else {
            throw new ResponseNotFoundException("Proyecto:", "ID:", tutorEmpresarialProyectoRequest.getIdProyecto() + "");
        }
        TutorEmpresarial dp = new TutorEmpresarial();
        CoordinadorCarrera cc = getCedula(tutorEmpresarialProyectoRequest.getCoordinador_id());
        dp.setCedula(tutorEmpresarialProyectoRequest.getCedula());
        dp.setCoordinadorCarrera(cc);
        dp.setEstado(tutorEmpresarialProyectoRequest.isEstado());
        TutorEmpresarial d;
        try {
            d = tutorEmpresarialProyectoRepository.save(dp);
            optional.get().setTutorEmpresarial(d);
            proyectoRepository.save(optional.get());

        } catch (Exception e) {
            throw new BadRequestException("No se guardó al director");
        }
        try {
            enviarCorreos(d);
            return true;
        } catch (Exception e) {
            throw new BadRequestException("No se envió el correo al director" + e);
        }
    }

    /**
     * Metodo para validar que existe en la tabla usuario y enviar correo electrónico
     *
     * @param tutorEmpresarial
     */
    public void enviarCorreos(TutorEmpresarial tutorEmpresarial) {
        Optional<Usuario> optional = usuarioRepository.findByCedula(tutorEmpresarial.getCedula());
        Optional<ProyectoPPP> proyecto = proyectoRepository.findByTutorEmpresarial(tutorEmpresarial);

        if (optional.isPresent()) {
            if (proyecto.isPresent()) {
                EmailBody e = new EmailBody();
                e.setEmail(optional.get().getEmail());
                e.setContent("Usted ha sido designado como tutor em\n" +
                        "Proyecto; " + proyecto.get().getNombre() + "\n");
                e.setText2(" Ingrese al sistema dando clic en el siguiente botón:");
                e.setSubject("Designación para proyectos de vinculación");
                emailService.sendEmail(e);
            } else {
                System.out.println("NO EXISTE PROYECTO");
            }

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

    public TutorEmpresarialProyectoResponse getDirector(Long idProyecto) {
        Optional<ProyectoPPP> op = proyectoRepository.findById(idProyecto);
        if (op.isPresent()) {
            Optional<TutorEmpresarial> optional = tutorEmpresarialProyectoRepository.findByProyectoPPPAndEstado(op.get(), true);
            if (optional.isPresent()) {
                Optional<VPersonasppp> per = personasRepository.findByCedula(optional.get().getCedula());
                if (per.isPresent()) {
                    TutorEmpresarialProyectoResponse dpr = new TutorEmpresarialProyectoResponse();
                    dpr.setNombre(per.get().getNombres());
                    dpr.setApellidos(per.get().getApellidos());
                    dpr.setCedula(per.get().getCedula());
                    dpr.setCorreo(correoPorUsuario(per.get().getCedula()));
                    return dpr;
                }
                throw new ResponseNotFoundException("PERSONA", "CEDULA:", optional.get().getCedula());
            }
            throw new ResponseNotFoundException("DIRECTOR", "IDProyetco:", idProyecto + "");
        }
        throw new ResponseNotFoundException("PROYECTO", "ID:", idProyecto + "");
    }

    private String correoPorUsuario(String cedula) {
        Optional<Usuario> optional = usuarioRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            return optional.get().getEmail();
        }
        return "";
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<TutorEmpresarial> optional = tutorEmpresarialProyectoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("El tutor em con el id: " + id + ", no existe");
        }
        tutorEmpresarialProyectoRepository.delete(optional.get());
    }

    @Transactional(readOnly = true)
    public DirectorCedulaResponse getCedulaDirectorByProject(Long id) {
        Optional<ProyectoPPP> optional = proyectoRepository.findById(id);
        if (optional.isEmpty())
            throw new BadRequestException("El proyecto con id: " + id + ", no existe");

        String cedula = optional.get().getTutorEmpresarial().getCedula();
        return new DirectorCedulaResponse(cedula);
    }
}
