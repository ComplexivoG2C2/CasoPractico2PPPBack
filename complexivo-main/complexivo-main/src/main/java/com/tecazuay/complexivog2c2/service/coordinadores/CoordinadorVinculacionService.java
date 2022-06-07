package com.tecazuay.complexivog2c2.service.coordinadores;


import com.tecazuay.complexivog2c2.dto.coordinadorVinculacion.VinculacionRequest;
import com.tecazuay.complexivog2c2.dto.coordinadorVinculacion.VinculacionResponse;
import com.tecazuay.complexivog2c2.dto.email.EmailBody;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.docentes.VDocentesByIdppp;
import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import com.tecazuay.complexivog2c2.repository.Primary.autoridades.AutoridadesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.coordinadores.CoordinadorVRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.docentes.DocentesByIdRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import com.tecazuay.complexivog2c2.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CoordinadorVinculacionService {

    @Autowired
    private CoordinadorVinculacionRepository coordinadorVinculacionRepository;
    @Autowired
    private PersonasRepository personasRepository;
    @Autowired
    private DocentesByIdRepository docenteByIdRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CoordinadorVRepository coordinadorVRepository;
    @Autowired
    private TutorAcademicoRepository tutorAcademicoRepository;
    @Autowired
    private TutorEmpProyectoRepository tutorEmpProyectoRepository;
    @Autowired
    private ResponsablePPPRepository responsablePPPRepository;
    @Autowired
    private AutoridadesRepository autoridadesRepository;


    /**
     * Metodo para crear un Coordinador de Vinculación
     * Validamos que si ya existe el Coordinador de Vinculación en la tabla de bd_vinculación
     * Si ya existe no se puede crear nuevamente, se valida de acuerdo a la cedula
     * En caso de que un docente se haya registrado antes de ser designado
     * al momento de ser designado se envia un correo electrónico
     * El email para enviar el correo se obtiene a través de la cédula
     * @param vinculacionRequest
     * @return Boolean (true-false)
     */
    public boolean save (VinculacionRequest vinculacionRequest){
        if(coordinadorVinculacionRepository.existsByCedulaAndEstado(vinculacionRequest.getCedula(),true)){
            log.info("Ya existe un CV con la cedula y estado {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Coordinador de Vinculación");
        }if(coordinadorVRepository.existsByCedula(vinculacionRequest.getCedula())){
            log.info("Ya existe un CC con esa cedula {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Coordinador de Carrera");
        }if(tutorAcademicoRepository.existsByCedulaAndEstado(vinculacionRequest.getCedula(),true)){
                log.info("Ya existe un DA con esa cedula {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Docente de Apoyo");
        }if(tutorEmpProyectoRepository.existsByCedulaAndEstado(vinculacionRequest.getCedula(),true)){
            log.info("Ya existe un DP con esa cedula {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Director de Proyecto");
        }if(responsablePPPRepository.existsByCedulaAndEstado(vinculacionRequest.getCedula(),true)){
            log.info("Ya existe un RPPP con esa cedula {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Responsable de Practicas");
        }if(autoridadesRepository.existsByCedulaAndEstado(vinculacionRequest.getCedula(),true)){
            log.info("Ya existe un AUT con esa cedula {}",vinculacionRequest.getCedula());
            throw new BadRequestException("Ya se encuentra asignado como Autoridad");
        }
        Optional<CoordinadorVinculacion> optional=coordinadorVinculacionRepository.findByCedula(vinculacionRequest.getCedula());
        if(optional.isPresent()){
            optional.get().setEstado(true);
            coordinadorVinculacionRepository.save(optional.get());
        }else{
            CoordinadorVinculacion cv = new CoordinadorVinculacion();
            cv.setCedula(vinculacionRequest.getCedula());
            cv.setEstado(vinculacionRequest.isEstado());
            coordinadorVinculacionRepository.save(cv);
        }


        Optional<Usuario> getEmail=usuarioRepository.findByCedula(vinculacionRequest.getCedula());
        if(getEmail.isPresent()){
            EmailBody e = new EmailBody();
            e.setEmail(List.of(getEmail.get().getEmail()));
            e.setContent("Usted ha sido designado como coordinador de vinculación" );
            e.setSubject("Designación para proyectos de vinculación");
            e.setText2("Ingrese al sistema dando clic en el siguiente botón:");
            emailService.sendEmail(e);
        }else{
            System.out.println("NO HAY EL EMAIL");
        }
        return true;
    }

    /**
     * Método para actualizar el estado de un Coordinador de Vinculación de acuerdo a la cédula
     * @param vinculacionRequest
     * @return Boolean (true-false)
     */
    public boolean act_estado(VinculacionRequest vinculacionRequest){
        Optional<CoordinadorVinculacion> optional=coordinadorVinculacionRepository.findByCedula(vinculacionRequest.getCedula());
                if(optional.isPresent()){
                    CoordinadorVinculacion cv = optional.get();
                    cv.setEstado(vinculacionRequest.isEstado());
                    coordinadorVinculacionRepository.save(cv);
                    return true;
                }
                return false;
    }

    /**
     * Método para obtener a todos los docentes de la base de Fénix
     * Validamos que exista en al tabla Personas según la cédula para obtener nombre y apellidos
     * Validamos el estado de un docente para comprabar si pertenece a un coordinador de vinculacion
     * @return Lista de docentes
     */
    public List<VinculacionResponse> listDocentes() {
        List<VDocentesByIdppp> listcv = docenteByIdRepository.findAll();
        return listcv.stream().map(VDocentesByIdppp -> {
            VinculacionResponse vinres = new VinculacionResponse();
            vinres.setCedula(VDocentesByIdppp.getCedula());
            vinres.setTitulo(VDocentesByIdppp.getDocente_titulo());
            vinres.setCarga(VDocentesByIdppp.getDocente_tipo_tiempo());
            VPersonasppp vp = personasRepository.findByCedula(VDocentesByIdppp.getCedula()).orElse(null);
            if (vp != null) {
                vinres.setNombres(vp.getNombres());
                vinres.setApellidos(vp.getApellidos());
            }
            Optional<CoordinadorVinculacion> optional = coordinadorVinculacionRepository.findByCedula(VDocentesByIdppp.getCedula());
            if (optional.isPresent()) {
                vinres.setEstado(optional.get().isEstado());
            } else {
                vinres.setEstado(false);
            }
            return vinres;
        }).collect(Collectors.toList());
    }

    /**
     * Método para listar todos los coordinadores de vinculaciónn designados
     * @return lista coordinadores de vinculacion
     */
    public List<VinculacionResponse>listCoordinadores(){
        List<CoordinadorVinculacion> cv =coordinadorVinculacionRepository.findAll();
        return cv.stream().map(coordinadorVinculacion -> {
            VinculacionResponse coor= new VinculacionResponse();
            coor.setCedula(coordinadorVinculacion.getCedula());
            coor.setNombres(getNombreCoordinador(coordinadorVinculacion.getCedula()));
            coor.setApellidos(getApellidoCoordinador(coordinadorVinculacion.getCedula()));
            coor.setTitulo(getTituloCoordinador(coordinadorVinculacion.getCedula()));
            coor.setEstado(coordinadorVinculacion.isEstado());
            coor.setCarga(getCargaCoordinador(coordinadorVinculacion.getCedula()));

            return coor;
        }).collect(Collectors.toList());
    }

    /**
     *Método para comprabar si ya existe un coordinador de vinculacion según la cédula
     * @param cedula
     * @return
     */
    public Boolean existsCV(String cedula){
        return coordinadorVinculacionRepository.existsByCedula(cedula);
    }

    /**
     *Obtenemos los nombres de los docentes desde la vista Personas base Fénix según la cedula
     * @param cedula
     * @return
     */
    public String getNombreCoordinador(String cedula){
            Optional<VPersonasppp> optionalP = personasRepository.findByCedula(cedula);
            if(optionalP.isPresent()){
                return optionalP.get().getNombres();
        }
        return " ";
    }

    /**
     * Obtenemos los apellidos de los docentes desde la vista Personas base Fénix según la cedula
     * @param cedula
     * @return
     */
    public String getApellidoCoordinador(String cedula){
            Optional<VPersonasppp> optionalP = personasRepository.findByCedula(cedula);
            if(optionalP.isPresent()){
                return optionalP.get().getApellidos();
            }
        return " ";
    }

    /**
     *  Obtenemos el titulo de los docentes desde la vista Docentes base Fénix según la cedula
     * @param cedula
     * @return
     */
    public String getTituloCoordinador(String cedula){
        Optional<VDocentesByIdppp> optionalP = docenteByIdRepository.findByCedula(cedula);
        if(optionalP.isPresent()){
            return optionalP.get().getDocente_titulo();
        }
        return " ";
    }

    /**
     * Obtenemos la acrga laboral de los docentes desde la vista Docentes base Fénix según la cedula
     * @param cedula
     * @return
     */
    public String getCargaCoordinador(String cedula){
        Optional<VDocentesByIdppp> optionalP = docenteByIdRepository.findByCedula(cedula);
        if(optionalP.isPresent()){
            return optionalP.get().getDocente_tipo_tiempo();
        }
        return " ";
    }

    public VinculacionResponse getCoordinador() {
        Optional<CoordinadorVinculacion> optional = coordinadorVinculacionRepository.findByEstado(true);
        if (optional.isPresent()) {
            VinculacionResponse response = new VinculacionResponse();
            response.setCedula(optional.get().getCedula());
            response.setEstado(optional.get().isEstado());
            response.setNombres(getNombreCoordinador(optional.get().getCedula()));
            response.setApellidos(getApellidoCoordinador(optional.get().getCedula()));
            response.setTitulo(getTituloCoordinador(optional.get().getCedula()));
            response.setCarga(getCargaCoordinador(optional.get().getCedula()));
            return response;
        }
        throw new ResponseNotFoundException("Coordinador Vinculación", "", "");
    }

    public VinculacionResponse getCoordinadorById(Long id){
        Optional<CoordinadorVinculacion> optional = coordinadorVinculacionRepository.findById(id);
        if(optional.isPresent()){
            VinculacionResponse response = new VinculacionResponse();
            response.setCedula(optional.get().getCedula());
            response.setEstado(optional.get().isEstado());
            response.setNombres(getNombreCoordinador(optional.get().getCedula()));
            response.setApellidos(getApellidoCoordinador(optional.get().getCedula()));
            response.setTitulo(getTituloCoordinador(optional.get().getCedula()));
            response.setCarga(getCargaCoordinador(optional.get().getCedula()));
            return response;
        }
        throw new ResponseNotFoundException("Coordinador Vinculación", "id", id+"");
    }
}
