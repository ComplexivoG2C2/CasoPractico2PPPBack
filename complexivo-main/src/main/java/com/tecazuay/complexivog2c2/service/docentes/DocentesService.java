package com.tecazuay.complexivog2c2.service.docentes;

import com.tecazuay.complexivog2c2.dto.carreras.CodigoCarrerasResponse;
import com.tecazuay.complexivog2c2.dto.docentes.*;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.ResponsableIdResponse;
import com.tecazuay.complexivog2c2.dto.email.EmailBody;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.docentes.VDocentespppall;
import com.tecazuay.complexivog2c2.repository.Primary.autoridades.AutoridadesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera.CoordinadorRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpresarialProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.CarrerasRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.VCarrerasDocentesRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.coordinadores.CoordinadorVRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.docentes.DocentesAllRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.materias.MateriasRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import com.tecazuay.complexivog2c2.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class DocentesService {

    private final DocentesAllRepository docenteAllRepository;
    private final CarrerasRepository carrerasRepository;
    private final MateriasRepository materiasRepository;
    private final TutorAcademicoRepository tutorAcademicoRepository;
    private final TutorEmpresarialProyectoRepository tutorEmpresarialProyectoRepository;
    private final ResponsablePPPRepository responsablePPPRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final ProyectoRepository proyectoRepository;
    private final CoordinadorVRepository coordinadorVRepository;
    private final CoordinadorVinculacionRepository coordinadorVinculacionRepository;
    private final AutoridadesRepository autoridadesRepository;
    private final VCarrerasDocentesRepository carrerasDocentesRepository;

    public DocenteResponse getByCedula(String cedula) {
        Optional<VDocentespppall> optional = docenteAllRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            DocenteResponse response = new DocenteResponse();
            response.setCedula(optional.get().getCedula());
            response.setNombres_completo(optional.get().getNombres() + " " + optional.get().getApellidos());
            response.setTitulo(optional.get().getDocente_titulo());
            response.setDocente_tipo_tiempo(optional.get().getDocente_tipo_tiempo());
            return response;
        }
        throw new ResponseNotFoundException("Docente", "cédula", cedula);
    }

    public ResponsableIdResponse getByCodigoCarrera(String codigoCarrera) {
        Optional<ResponsablePPP> responsablePPP = responsablePPPRepository.findByEstadoAndCodigoCarrera(true, codigoCarrera);
        if (responsablePPP.isPresent()) {
            Optional<VDocentespppall> optional = docenteAllRepository.findByCedula(responsablePPP.get().getCedula());
            if (optional.isPresent()) {
                ResponsableIdResponse response = new ResponsableIdResponse();
                response.setCedula(optional.get().getCedula());
                response.setNombres_completo(optional.get().getNombres() + " " + optional.get().getApellidos());
                response.setTitulo(optional.get().getDocente_titulo());
                response.setDocente_tipo_tiempo(optional.get().getDocente_tipo_tiempo());
                response.setId(responsablePPP.get().getId());
                response.setFecha_inicio_periodo(responsablePPP.get().getFecha_inicio_periodo());
                response.setFecha_fin_periodo(responsablePPP.get().getFecha_fin_periodo());
                response.setFecha_designacion(responsablePPP.get().getFecha_designacion());
                return response;
            }
        }
        throw new ResponseNotFoundException("Responsable PPP", "Código Carrera", codigoCarrera);
    }


    @Transactional
    public boolean saveRolesApoyo(DocenteRolesList docenteRolesList, CoordinadorCarrera coordinadorCarrera) {

        List<TutorAcademicoDelegados> listDocentesApoyo =
                docenteRolesList.getDocentes()
                        .stream()
                        .filter(docenteRequest -> docenteRequest.getCargo().equalsIgnoreCase("apoyo"))
                        .map(docenteRequest -> {
                            if (coordinadorVinculacionRepository.existsByCedulaAndEstado(docenteRequest.getCedula(), true)) {
                                log.info("Ya existe un CV con la cedula y estado {}", docenteRequest.getCedula());
                                throw new BadRequestException("Ya se encuentra asignado como Coordinador de Vinculación");
                            }
                            if (coordinadorVRepository.existsByCedula(docenteRequest.getCedula())) {
                                log.info("Ya existe un CC con esa cedula {}", docenteRequest.getCedula());
                                throw new BadRequestException("Ya se encuentra asignado como Coordinador de Carrera");
                            }
                            if (tutorEmpresarialProyectoRepository.existsByCedulaAndEstado(docenteRequest.getCedula(), true)) {
                                log.info("Ya existe un DP con esa cedula {}", docenteRequest.getCedula());
                                throw new BadRequestException("Ya se encuentra asignado como Director de Proyecto");
                            }
                            if (responsablePPPRepository.existsByCedulaAndEstado(docenteRequest.getCedula(), true)) {
                                log.info("Ya existe un RPPP con esa cedula {}", docenteRequest.getCedula());
                                throw new BadRequestException("Ya se encuentra asignado como Responsable de Practicas");
                            }
                            if (autoridadesRepository.existsByCedulaAndEstado(docenteRequest.getCedula(), true)) {
                                log.info("Ya existe un AUT con esa cedula {}", docenteRequest.getCedula());
                                throw new BadRequestException("Ya se encuentra asignado como Autoridad");
                            }
                            Optional<ProyectoPPP> optional = proyectoRepository.findById(docenteRolesList.getIdProyecto());
                            if (optional.isPresent()) {
                                if (tutorAcademicoRepository.existsByProyectoPPPAndCedula(optional.get(), docenteRequest.getCedula())) {
                                    throw new BadRequestException("Ya se encuentra asignado a este Proyecto");
                                }
                                if (docenteAllRepository.existsByCedula(docenteRequest.getCedula())) {
                                    TutorAcademicoDelegados da = new TutorAcademicoDelegados();
                                    da.setCedula(docenteRequest.getCedula());
                                    da.setCoordinadorCarrera(coordinadorCarrera);
                                    da.setEstado(docenteRequest.isEstado());
                                    optional.get().getTutorAcademicoDelegados().add(da);
                                    da.setProyectoPPP(optional.get());
                                    return da;
                                }
                            }
                            return null;
                        }).collect(Collectors.toList());

        listDocentesApoyo.removeIf(Objects::isNull);

        if (tutorAcademicoRepository.saveAll(listDocentesApoyo).size() > 0) {
            listDocentesApoyo.stream().forEach(docentesApoyoDelegados -> {
                Optional<Usuario> getEmail = usuarioRepository.findByCedula(docentesApoyoDelegados.getCedula());
                if (getEmail.isPresent()) {
                    EmailBody e = new EmailBody();
                    e.setEmail(getEmail.get().getEmail());
                    e.setContent("Usted ha sido designado como docente de apoyo");
                    e.setText2(" Ingrese al sistema dando clic en el siguiente botón:");
                    e.setSubject("Designación para proyectos de vinculación");
                    emailService.sendEmail(e);
                } else {
                    System.out.println("NO HAY EL EMAIL");
                }
            });
            return true;
        }
        log.error("Error al guardar docentes de apoyo");
        return false;
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
     * Obtener lista de docentes con sus respectivas materias y carreras segun Fénix
     *
     * @return
     */
    public List<DocentesMateriasList> listaDocentes() {
        List<VDocentespppall> listabase = docenteAllRepository.findAll();
        return listabase.stream().map(vDocentesppp -> {
            DocentesMateriasList materiasList = new DocentesMateriasList();
            materiasList.setMaterias(materiaNombreList(vDocentesppp.getCedula()));
            materiasList.setNombres_completo(vDocentesppp.getNombres() + " " + vDocentesppp.getApellidos());
            materiasList.setTitulo(vDocentesppp.getDocente_titulo());
            materiasList.setCedula(vDocentesppp.getCedula());
            materiasList.setCarreas(listaCarreas(vDocentesppp.getCedula()));
            materiasList.setDocente_tipo_tiempo(vDocentesppp.getDocente_tipo_tiempo());
            return materiasList;
        }).collect(Collectors.toList());
    }

    /**
     * Listar la smaterias segun el cédula del docente
     *
     * @param cedula
     * @return
     */
    public List<MateriaNombre> materiaNombreList(String cedula) {
        return materiasRepository.findByCedula(cedula).stream().map(vMateriasppp -> {
            MateriaNombre nombre = new MateriaNombre();
            nombre.setNombre(vMateriasppp.getMateria_nombre());
            return nombre;
        }).collect(Collectors.toList());
    }


    public List<CarreraNombre> listaCarreas(String cedula) {
        return carrerasRepository.findByCedula(cedula).stream().map(vMateriasppp -> {
            CarreraNombre nombre = new CarreraNombre();
            nombre.setNombrecarrera(vMateriasppp.getNombre_carrera());
            return nombre;
        }).collect(Collectors.toList());
    }

    /**
     * Método para guardar el rol de docentes de tutores
     *
     * @param docenteRolesList
     * @return
     */
    public boolean saveRoles(DocenteRolesList docenteRolesList) {
        CoordinadorCarrera coordinadorCarrera = getCedula(docenteRolesList.getCoordinador_id());
        saveRolesApoyo(docenteRolesList, coordinadorCarrera);
        return true;
    }

    /**
     * Enviar codigo de carrera de cada coordinador
     *
     * @param cedula
     * @return
     */
    public List<CodigoCarrerasResponse> enviarSiglasCarrera(String cedula) {
        if (coordinadorVRepository.existsByCedula(cedula)) {
            return coordinadorVRepository.findByCedula(cedula).stream().map(vCoordinadoresppp -> {
                CodigoCarrerasResponse cr = new CodigoCarrerasResponse();
                cr.setCodigo(vCoordinadoresppp.getCarreraCodigo());
                return cr;
            }).collect(Collectors.toList());
        } else {
            throw new ResponseNotFoundException("Coordinador Carrera", "Cedula: ", cedula);
        }
    }

    public void deleteDocenteApoyoById(Long id) {
        Optional<TutorAcademicoDelegados> optional = tutorAcademicoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("El docente de apoyo con id: " + id + ", no existe");
        }
        tutorAcademicoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CodigoCarrerasResponse> carrerasPorCedula(String cedula) {
        return carrerasDocentesRepository
                .findAllByCedula(cedula)
                .stream()
                .map(c -> new CodigoCarrerasResponse(c.getCodigoCarrera()))
                .collect(Collectors.toList());
    }
}
