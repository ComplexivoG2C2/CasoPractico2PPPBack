package com.tecazuay.complexivog2c2.service.Anexos;

import com.tecazuay.complexivog2c2.dto.alumnos.AlumnosCiclosResponse;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo3Request;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo3Response;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.Anexos.Anexo3;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Secondary.alumnos.VCicloAlumnos;
import com.tecazuay.complexivog2c2.repository.Primary.Anexos.Anexo3Repository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.alumnos.VCicloAlumnosRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.CarrerasRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Anexo3Service {

    @Autowired
    private Anexo3Repository anexo3Repository;
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ResponsablePPPRepository responsablePPPRepository;
    @Autowired
    private PersonasRepository personasRepository;
    @Autowired
    private VCicloAlumnosRepository vCicloAlumnosRepository;
    @Autowired
    private CarrerasRepository carrerasRepository;

    public AlumnosCiclosResponse getDatosAlumno (String cedula){
        Optional<VCicloAlumnos> optional=vCicloAlumnosRepository.findFirstByCedulaOrderByCicloDesc(cedula);
        if(optional.isPresent()){
            AlumnosCiclosResponse a = new AlumnosCiclosResponse();
            a.setCedula(optional.get().getCedula());
            a.setPrimerApellido(optional.get().getPrimerApellido());
            a.setSegundoApellido(optional.get().getSegundoApellido());
            a.setPrimerNombre(optional.get().getPrimerNombre());
            a.setSegundoNombre(optional.get().getSegundoNombre());
            a.setCodigoCarrera(optional.get().getCodigoCarrera());
            a.setNombreCarrera(optional.get().getNombreCarrera());
            a.setCiclo(optional.get().getCiclo());
            a.setParalelo(optional.get().getParalelo());
            a.setJornada(optional.get().getJornada());
            return a;
        }
        throw  new ResponseNotFoundException("ALUMNO","CEDULA",cedula);
    }
    public boolean save(Anexo3Request anexo3Request) {
        Optional<ProyectoPPP> optional = proyectoRepository.findById(anexo3Request.getIdProyectoPPP());
        if (optional.isPresent()) {
            if (personasRepository.existsByCedula(anexo3Request.getCedula())) {
                Anexo3 an = new Anexo3();
                an.setFechaSolicitud(anexo3Request.getFecha_solicitud());
                an.setDocenteTitulo(anexo3Request.getTitulo_responsable());
                an.setNombreReponsable(anexo3Request.getNombre_responsable());
                an.setSiglasCarrera(anexo3Request.getSiglas_carrera());
                an.setNombreEstudiante(anexo3Request.getNombresestudiante());
                an.setApellidoEstudiante(anexo3Request.getApellidosestudiante());
                an.setCedulaEstudiante(anexo3Request.getCedula());
                an.setNombreCarrera(anexo3Request.getNombrecarrera());
                an.setParalelo(anexo3Request.getParalelo());
                an.setJornada(anexo3Request.getJornada());
                an.setNombreProyecto(anexo3Request.getNombreproyecto());
                an.setDocumento(anexo3Request.getDocumento());
                an.setEstado(anexo3Request.getEstado());
                an.setProyectoPPP(proyectoRepository.findById(anexo3Request.getIdProyectoPPP()).orElse(new ProyectoPPP()));
                try {
                    anexo3Repository.save(an);
                    return true;
                } catch (Exception ex) {
                    throw new BadRequestException("No se guardó el anexo 3" + ex);
                }
            } else {
                throw new ResponseNotFoundException("Estudiante", "CEDULA:", "" + anexo3Request.getCedula());
            }

        }
        throw new BadRequestException("No existe el proyecto con id: " + anexo3Request.getIdProyectoPPP());
    }


    public Boolean update(Anexo3Request anexo3Request) {
        Optional<Anexo3> op = anexo3Repository.findById(anexo3Request.getId());
        if (op.isPresent()) {
            op.get().setFechaSolicitud(anexo3Request.getFecha_solicitud());
            op.get().setDocenteTitulo(anexo3Request.getTitulo_responsable());
            op.get().setNombreReponsable(anexo3Request.getNombre_responsable());
            op.get().setSiglasCarrera(anexo3Request.getSiglas_carrera());
            op.get().setNombreEstudiante(anexo3Request.getNombresestudiante());
            op.get().setApellidoEstudiante(anexo3Request.getApellidosestudiante());
            op.get().setCedulaEstudiante(anexo3Request.getCedula());
            op.get().setNombreCarrera(anexo3Request.getNombrecarrera());
            op.get().setParalelo(anexo3Request.getParalelo());
            op.get().setJornada(anexo3Request.getJornada());
            op.get().setNombreProyecto(anexo3Request.getNombreproyecto());
            op.get().setDocumento(anexo3Request.getDocumento());
            op.get().setEstado(anexo3Request.getEstado());
            op.get().setProyectoPPP(proyectoRepository.findById(anexo3Request.getIdProyectoPPP()).orElse(new ProyectoPPP()));
            try {
                Anexo3 aan1 = anexo3Repository.save(op.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó el anexo 1" + ex);
            }
        }
        throw new ResponseNotFoundException("Anexo1", "ID:", "" + anexo3Request.getId());
    }

    //Listar todos los anexos3
    @Transactional
    public List<Anexo3Response> listAnexoProyecto(Long id) {
        Optional<ProyectoPPP> op = proyectoRepository.findById(id);
        if (op.isPresent()) {
            List<Anexo3> lista = anexo3Repository.findAllByProyectoPPP(op.get());
            return lista.stream().map(anexo3 -> {
                Anexo3Response an = new Anexo3Response();
                an.setId(anexo3.getId());
                an.setFecha_solicitud(anexo3.getFechaSolicitud());
                an.setTitulo_responsable(anexo3.getDocenteTitulo());
                an.setNombre_responsable(anexo3.getNombreReponsable());
                an.setSiglas_carrera(anexo3.getSiglasCarrera());
                an.setNombresestudiante(anexo3.getNombreEstudiante());
                an.setApellidosestudiante(anexo3.getApellidoEstudiante());
                an.setCedula(anexo3.getCedulaEstudiante());
                an.setNombrecarrera(anexo3.getNombreCarrera());
                an.setParalelo(anexo3.getParalelo());
                an.setJornada(anexo3.getJornada());
                an.setNombreproyecto(anexo3.getNombreProyecto());
                an.setDocumento(anexo3.getDocumento());
                an.setEstado(anexo3.getEstado());
                an.setIdProyectoPPP(anexo3.getProyectoPPP().getId());
                return an;
            }).collect(Collectors.toList());

        }
        throw new BadRequestException("No existe el proyecto");
    }

    @Transactional
    public List<Anexo3Response> listAnexo3Carrera(String codigoCarrera){
        if(carrerasRepository.existsByCarreraCodigo(codigoCarrera)){
            List<Anexo3> lista= anexo3Repository.findAll();
            return lista.stream().map(anexo3 -> {
                Anexo3Response an = new Anexo3Response();
                an.setId(anexo3.getId());
                an.setFecha_solicitud(anexo3.getFechaSolicitud());
                an.setTitulo_responsable(anexo3.getDocenteTitulo());
                an.setNombre_responsable(anexo3.getNombreReponsable());
                an.setSiglas_carrera(anexo3.getSiglasCarrera());
                an.setNombresestudiante(anexo3.getNombreEstudiante());
                an.setApellidosestudiante(anexo3.getApellidoEstudiante());
                an.setCedula(anexo3.getCedulaEstudiante());
                an.setNombrecarrera(anexo3.getNombreCarrera());
                an.setParalelo(anexo3.getParalelo());
                an.setEstado(anexo3.getEstado());
                an.setJornada(anexo3.getJornada());
                an.setNombreproyecto(anexo3.getNombreProyecto());
                an.setDocumento(anexo3.getDocumento());
                an.setIdProyectoPPP(anexo3.getProyectoPPP().getId());
                return an;
            }).collect(Collectors.toList());
        }
        throw new BadRequestException("No existe el estudiante ");
    }
    @Transactional
    public List<Anexo3Response> listAnexo(String cedula) {
        if (usuarioRepository.existsByCedula(cedula)) {
            List<Anexo3> lista = anexo3Repository.findAllByCedulaEstudiante(cedula);
            return lista.stream().map(anexo3 -> {
                Anexo3Response an = new Anexo3Response();
                an.setId(anexo3.getId());
                an.setFecha_solicitud(anexo3.getFechaSolicitud());
                an.setTitulo_responsable(anexo3.getDocenteTitulo());
                an.setNombre_responsable(anexo3.getNombreReponsable());
                an.setSiglas_carrera(anexo3.getSiglasCarrera());
                an.setNombresestudiante(anexo3.getNombreEstudiante());
                an.setApellidosestudiante(anexo3.getApellidoEstudiante());
                an.setCedula(anexo3.getCedulaEstudiante());
                an.setNombrecarrera(anexo3.getNombreCarrera());
                an.setParalelo(anexo3.getParalelo());
                an.setEstado(anexo3.getEstado());
                an.setJornada(anexo3.getJornada());
                an.setNombreproyecto(anexo3.getNombreProyecto());
                an.setDocumento(anexo3.getDocumento());
                an.setIdProyectoPPP(anexo3.getProyectoPPP().getId());
                return an;
            }).collect(Collectors.toList());
        }
        throw new BadRequestException("No existe el estudiante ");
    }
}
