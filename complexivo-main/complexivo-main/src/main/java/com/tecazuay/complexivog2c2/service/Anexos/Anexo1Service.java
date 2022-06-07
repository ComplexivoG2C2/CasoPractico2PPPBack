package com.tecazuay.complexivog2c2.service.Anexos;

import com.tecazuay.complexivog2c2.dto.anexos.Anexo1Request;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo1Response;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.Anexos.Anexo1;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.repository.Primary.Anexos.Anexo1Repository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.CarrerasAllRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Anexo1Service {

    private final Anexo1Repository anexo1Repository;
    private final ProyectoRepository proyectoRepository;
    private final TutorAcademicoRepository tutorAcademicoRepository;
    private final TutorEmpProyectoRepository tutorEmpProyectoRepository;
    private final PersonasRepository personasRepository;
    private final CarrerasAllRepository carrerasAllRepository;

    @Transactional
    public boolean save(Anexo1Request anexo1Request) {
        Optional<ProyectoPPP> optional = proyectoRepository.findById(anexo1Request.getIdProyectoPPP());
        if (optional.isPresent()) {
            if (!optional.get().isEstado())
                throw new BadRequestException("El proyecto ha finalizado, no es posible modificar sus datos");

            Anexo1 an = new Anexo1();
            if (personasRepository.existsByCedula(anexo1Request.getCedulaDelegado())) {
                if (personasRepository.existsByCedula(anexo1Request.getCedulaCoordinador())) {
                    an.setFechaDelegacion(anexo1Request.getFechaDelegacion());
                    an.setDocenteTitulo(anexo1Request.getDocenteTitulo());
                    an.setCedulaDelegado(anexo1Request.getCedulaDelegado());
                    an.setNombreDelegado(anexo1Request.getNombreDelegado());
                    an.setNombreCarrera(anexo1Request.getNombreCarrera());
                    an.setNombreRol(anexo1Request.getNombreRol());
                    an.setNombreProyecto(anexo1Request.getNombreProyecto());
                    an.setCedulaCoordinador(anexo1Request.getCedulaCoordinador());
                    an.setNombreCoordinador(anexo1Request.getNombreCoordinador());
                    an.setSiglasCarrera(anexo1Request.getSiglasCarrera());
                    an.setFechaDelegado(anexo1Request.getFechaDelegado());
                    an.setNumProceso(anexo1Request.getNumProceso());
                    an.setProyectoPPP(proyectoRepository.findById(anexo1Request.getIdProyectoPPP()).orElse(new ProyectoPPP()));
                    an.setDocumento(anexo1Request.getDocumento());
                    try {
                        anexo1Repository.save(an);
                        return true;
                    } catch (Exception ex) {
                        throw new BadRequestException("No se guardó el anexo 1" + ex);
                    }
                } else {
                    throw new ResponseNotFoundException("Coordinador", "CEDULA:", "" + anexo1Request.getCedulaCoordinador());
                }
            } else {
                throw new ResponseNotFoundException("Docente Delegado", "CEDULA:", "" + anexo1Request.getCedulaDelegado());
            }
        }
        throw new BadRequestException("No existe el proyecto con id: " + anexo1Request.getIdProyectoPPP());
    }

    public Boolean update(Anexo1Request anexo1Request) {
        Optional<Anexo1> op = anexo1Repository.findById(anexo1Request.getId());
        if (op.isPresent()) {
            if (!op.get().getProyectoPPP().isEstado())
                throw new BadRequestException("El proyecto ha finalizado, no es posible modificar sus datos");

            if (personasRepository.existsByCedula(anexo1Request.getCedulaDelegado())) {
                if (personasRepository.existsByCedula(anexo1Request.getCedulaCoordinador())) {
                    op.get().setFechaDelegacion(anexo1Request.getFechaDelegacion());
                    op.get().setDocenteTitulo(anexo1Request.getDocenteTitulo());
                    op.get().setCedulaDelegado(anexo1Request.getCedulaDelegado());
                    op.get().setNombreDelegado(anexo1Request.getNombreDelegado());
                    op.get().setNombreCarrera(anexo1Request.getNombreCarrera());
                    op.get().setNombreRol(anexo1Request.getNombreRol());
                    op.get().setNombreProyecto(anexo1Request.getNombreProyecto());
                    op.get().setCedulaCoordinador(anexo1Request.getCedulaCoordinador());
                    op.get().setNombreCoordinador(anexo1Request.getNombreCoordinador());
                    op.get().setSiglasCarrera(anexo1Request.getSiglasCarrera());
                    op.get().setFechaDelegado(anexo1Request.getFechaDelegado());
                    op.get().setNumProceso(anexo1Request.getNumProceso());
                    op.get().setProyectoPPP(proyectoRepository.findById(anexo1Request.getIdProyectoPPP()).orElse(new ProyectoPPP()));
                    op.get().setDocumento(anexo1Request.getDocumento());
                    try {
                        Anexo1 aan1 = anexo1Repository.save(op.get());
                        return true;
                    } catch (Exception ex) {
                        throw new BadRequestException("No se actualizó el anexo 1" + ex);
                    }
                } else {
                    throw new ResponseNotFoundException("Coordinador", "CEDULA:", "" + anexo1Request.getCedulaCoordinador());
                }

            } else {
                throw new ResponseNotFoundException("Docente Delegado", "CEDULA:", "" + anexo1Request.getCedulaDelegado());
            }
        }

        throw new ResponseNotFoundException("Anexo1", "ID:", "" + anexo1Request.getId());

    }

    //Listar todos los anexos1
    public List<Anexo1Response> listAnexoProyecto(Long id) {
        Optional<ProyectoPPP> op = proyectoRepository.findById(id);
        if (op.isPresent()) {
            List<Anexo1> lista = anexo1Repository.findAllByProyectoPPP(op.get());
            return lista.stream().map(anexo1 -> {
                Anexo1Response an = new Anexo1Response();
                an.setId(anexo1.getId());
                an.setFechaDelegacion(anexo1.getFechaDelegacion());
                an.setDocenteTitulo(anexo1.getDocenteTitulo());
                an.setCedulaDelegado(anexo1.getCedulaDelegado());
                an.setNombreDelegado(anexo1.getNombreDelegado());
                an.setNombreCarrera(anexo1.getNombreCarrera());
                an.setNombreRol(anexo1.getNombreRol());
                an.setNombreProyecto(anexo1.getNombreProyecto());
                an.setCedulaCoordinador(anexo1.getCedulaCoordinador());
                an.setNombreCoordinador(anexo1.getNombreCoordinador());
                an.setSiglasCarrera(anexo1.getSiglasCarrera());
                an.setFechaDelegado(anexo1.getFechaDelegado());
                an.setNumProceso(anexo1.getNumProceso());
                an.setIdProyectoPPP(anexo1.getProyectoPPP().getId());
                an.setDocumento(anexo1.getDocumento());
                return an;
            }).collect(Collectors.toList());

        }
        throw new BadRequestException("No existe el proyecto");
    }

    public List<Anexo1Response> listByCarreraCodigo(String codigo) {
        if (carrerasAllRepository.existsByCarreraCodigo(codigo)) {
            List<Anexo1> lista = anexo1Repository.findAllBySiglasCarrera(codigo);
            return lista.stream().map(anexo1 -> {
                Anexo1Response an = new Anexo1Response();
                an.setId(anexo1.getId());
                an.setFechaDelegacion(anexo1.getFechaDelegacion());
                an.setDocenteTitulo(anexo1.getDocenteTitulo());
                an.setCedulaDelegado(anexo1.getCedulaDelegado());
                an.setNombreDelegado(anexo1.getNombreDelegado());
                an.setNombreCarrera(anexo1.getNombreCarrera());
                an.setNombreRol(anexo1.getNombreRol());
                an.setNombreProyecto(anexo1.getNombreProyecto());
                an.setCedulaCoordinador(anexo1.getCedulaCoordinador());
                an.setNombreCoordinador(anexo1.getNombreCoordinador());
                an.setSiglasCarrera(anexo1.getSiglasCarrera());
                an.setFechaDelegado(anexo1.getFechaDelegado());
                an.setNumProceso(anexo1.getNumProceso());
                an.setIdProyectoPPP(anexo1.getProyectoPPP().getId());
                an.setDocumento(anexo1.getDocumento());
                return an;
            }).collect(Collectors.toList());
        } else {
            throw new BadRequestException("No existe el codigo de carrera");
        }
    }

    public List<Anexo1Response> listAnexoDocente(String cedula) {
        if (tutorAcademicoRepository.existsByCedula(cedula) || tutorEmpProyectoRepository.existsByCedula(cedula)) {
            List<Anexo1> lista = anexo1Repository.findAllByCedulaDelegado(cedula);
            return lista.stream().map(anexo1 -> {
                Anexo1Response an = new Anexo1Response();
                an.setId(anexo1.getId());
                an.setFechaDelegacion(anexo1.getFechaDelegacion());
                an.setDocenteTitulo(anexo1.getDocenteTitulo());
                an.setCedulaDelegado(anexo1.getCedulaDelegado());
                an.setNombreDelegado(anexo1.getNombreDelegado());
                an.setNombreCarrera(anexo1.getNombreCarrera());
                an.setNombreRol(anexo1.getNombreRol());
                an.setNombreProyecto(anexo1.getNombreProyecto());
                an.setCedulaCoordinador(anexo1.getCedulaCoordinador());
                an.setNombreCoordinador(anexo1.getNombreCoordinador());
                an.setSiglasCarrera(anexo1.getSiglasCarrera());
                an.setFechaDelegado(anexo1.getFechaDelegado());
                an.setNumProceso(anexo1.getNumProceso());
                an.setIdProyectoPPP(anexo1.getProyectoPPP().getId());
                an.setDocumento(anexo1.getDocumento());
                return an;
            }).collect(Collectors.toList());
        }
        throw new BadRequestException("No existe el docente ");
    }

    public void deleteAnexosList(List<Anexo1> anexo1) {
        anexo1Repository.deleteAll(anexo1);
    }

    @Transactional
    public void deleteAllByProyectId(Long id) {
        Optional<ProyectoPPP> optional = proyectoRepository.findById(id);
        if (optional.isEmpty())
            throw new BadRequestException("El proyecto con id: " + id + ", no existe");

        if (!optional.get().isEstado())
            throw new BadRequestException("El proyecto ha finalizado, no es posible modificar sus datos");

        if (optional.get().getAnexo1() != null) {

            optional.get().getAnexo1().forEach(anexo1Repository::delete);
        }
    }
}
