package com.tecazuay.complexivog2c2.service.materia;

import com.tecazuay.complexivog2c2.dto.materias.MateriaCarreraResponse;
import com.tecazuay.complexivog2c2.dto.materias.MateriaNombre;
import com.tecazuay.complexivog2c2.dto.materias.MateriasAlumnoList;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.CarrerasRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.materias.MateriasAllRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.materias.VMateriasAlumnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    private MateriasAllRepository materiasRepository;

    @Autowired
    private CarrerasRepository carrerasRepository;

    @Autowired
    private VMateriasAlumnosRepository materiasAlumnosRepository;

    public List<MateriaCarreraResponse> findAllByCarrera(String carreraCodigo) {
        if (carrerasRepository.existsByCarreraCodigo(carreraCodigo)) {
            return materiasRepository.findAllByCarreraCodigo(carreraCodigo).stream()
                    .map(materia -> {
                        MateriaCarreraResponse response = new MateriaCarreraResponse();
                        response.setCodigo(materia.getMateriaCodigo());
                        response.setNombre(materia.getMateria_nombre());
                        response.setId(materia.getId_materia());
                        return response;
                    }).collect(Collectors.toList());
        }
        throw new BadRequestException("No existe la carrera con el código: " + carreraCodigo);
    }

    public MateriasAlumnoList materiasPorCedula(String cedula) {
        MateriasAlumnoList alumnoList = new MateriasAlumnoList();
        alumnoList.setCedula(cedula);
        alumnoList.setMaterias(materiasNombres(cedula));
        return alumnoList;
    }

    @Transactional(readOnly = true)
    public List<MateriaNombre> materiasNombres(String cedula) {
        return materiasAlumnosRepository.findAllByCedula(cedula)
                .stream().map(materia -> {
                    MateriaNombre materiaNombre = new MateriaNombre();
                    materiaNombre.setNombre(materia.getMateriaNombre());
                    return materiaNombre;
                }).collect(Collectors.toList());
    }

}
