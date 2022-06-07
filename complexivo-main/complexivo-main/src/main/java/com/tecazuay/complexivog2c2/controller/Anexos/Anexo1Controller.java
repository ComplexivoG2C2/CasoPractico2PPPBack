package com.tecazuay.complexivog2c2.controller.Anexos;

import com.tecazuay.complexivog2c2.dto.anexos.Anexo1Request;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo1Response;
import com.tecazuay.complexivog2c2.exception.Mensaje;
import com.tecazuay.complexivog2c2.service.Anexos.Anexo1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/anexo1")
@RequiredArgsConstructor
public class Anexo1Controller {

    private final Anexo1Service anexo1Service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Anexo1Request anexo1Request) {
        anexo1Service.save(anexo1Request);
        return new ResponseEntity(new Mensaje("Anexo 1 Creado"), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Anexo1Request anexo1Request) {
        anexo1Service.update(anexo1Request);
        return new ResponseEntity(new Mensaje("Anexo 1 Actualiazado"), HttpStatus.CREATED);
    }

    @GetMapping("/allByProyecto/{idProyecto}")
    public ResponseEntity<List<Anexo1Response>> listAnexosPro(@PathVariable Long idProyecto) {
        List<Anexo1Response> anexos = anexo1Service.listAnexoProyecto(idProyecto);
        return new ResponseEntity<>(anexos, HttpStatus.OK);
    }

    @GetMapping("/allByCedula/{cedula}")
    public ResponseEntity<List<Anexo1Response>> listAnexosCedula(@PathVariable String cedula) {
        List<Anexo1Response> anexos = anexo1Service.listAnexoDocente(cedula);
        return new ResponseEntity<>(anexos, HttpStatus.OK);
    }

    @GetMapping("/allByCarrera/{codigo}")
    public ResponseEntity<List<Anexo1Response>> listAnexosCodigo(@PathVariable String codigo) {
        List<Anexo1Response> anexos = anexo1Service.listByCarreraCodigo(codigo);
        return new ResponseEntity<>(anexos, HttpStatus.OK);
    }

    @DeleteMapping("/proyecto/{id}")
    public ResponseEntity<?> deleteByProjectId(@PathVariable Long id) {
        anexo1Service.deleteAllByProyectId(id);
        return new ResponseEntity<>(new Mensaje("Anexos 1 eliminados"), HttpStatus.OK);
    }

}
