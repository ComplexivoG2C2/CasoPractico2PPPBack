package com.tecazuay.complexivog2c2.controller;

import com.tecazuay.complexivog2c2.dto.coordinadorVinculacion.VinculacionRequest;
import com.tecazuay.complexivog2c2.dto.coordinadorVinculacion.VinculacionResponse;
import com.tecazuay.complexivog2c2.exception.Mensaje;
import com.tecazuay.complexivog2c2.service.coordinadores.CoordinadorVinculacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/vinculacion")
public class CoordinacionVinculacionController {

    @Autowired
    private CoordinadorVinculacionService coordinadorVinculacionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VinculacionRequest vinculacionRequest) {
        if (coordinadorVinculacionService.save(vinculacionRequest)) {
            return new ResponseEntity(new Mensaje("Coordinación Vinculación Creada"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new Mensaje("Ya existe"), HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody VinculacionRequest vinculacionRequest) {
        coordinadorVinculacionService.act_estado(vinculacionRequest);
        return new ResponseEntity(new Mensaje("Coordinación Vinculación Actualizado"), HttpStatus.OK);
    }

    @GetMapping("/all/docentes")
    public ResponseEntity<List<VinculacionResponse>> listDocentes() {
        List<VinculacionResponse> list = coordinadorVinculacionService.listDocentes();
        return new ResponseEntity<List<VinculacionResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VinculacionResponse>> listCoordiandores() {
        List<VinculacionResponse> list = coordinadorVinculacionService.listCoordinadores();
        return new ResponseEntity<List<VinculacionResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/exists/{cedula}")
    public ResponseEntity<Boolean> exists(@PathVariable String cedula) {
        return new ResponseEntity<Boolean>(coordinadorVinculacionService.existsCV(cedula), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCoordinador() {
        return new ResponseEntity<>(coordinadorVinculacionService.getCoordinador(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VinculacionResponse> listCVById(@PathVariable Long id) {
        VinculacionResponse cv = coordinadorVinculacionService.getCoordinadorById(id);
        return new ResponseEntity<>(cv, HttpStatus.OK);
    }
}
