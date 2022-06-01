package com.tecazuay.complexivog2c2.controller;

import com.tecazuay.complexivog2c2.dto.materias.MateriaCarreraResponse;
import com.tecazuay.complexivog2c2.dto.materias.MateriasAlumnoList;
import com.tecazuay.complexivog2c2.service.materia.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping("/{carreraCodigo}")
    public ResponseEntity<List<MateriaCarreraResponse>> getAllByCarrera(@PathVariable String carreraCodigo) {
        return new ResponseEntity<>(materiaService.findAllByCarrera(carreraCodigo), HttpStatus.OK);
    }

    @GetMapping("/alumno/{cedula}")
    public ResponseEntity<MateriasAlumnoList> materiasPorCedula(@PathVariable String cedula) {
        return new ResponseEntity<>(materiaService.materiasPorCedula(cedula), HttpStatus.OK);
    }
}
