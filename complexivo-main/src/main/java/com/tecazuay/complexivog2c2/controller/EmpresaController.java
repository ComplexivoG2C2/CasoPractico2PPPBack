package com.tecazuay.complexivog2c2.controller;


import com.tecazuay.complexivog2c2.dto.empresa.EmpresaRequest;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaResponse;
import com.tecazuay.complexivog2c2.dto.empresa.RepresentanteResponse;
import com.tecazuay.complexivog2c2.exception.Mensaje;
import com.tecazuay.complexivog2c2.service.empresa.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmpresaRequest empresaRequest){
        empresaService.save(empresaRequest);
        return new ResponseEntity(new Mensaje("Empresa Creada"), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody EmpresaRequest empresaRequest){
        empresaService.update(empresaRequest);
        return new ResponseEntity(new Mensaje("Empresa Actualizada"), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmpresaResponse>>listEntidades(){
        List<EmpresaResponse> entidades = empresaService.listEntidad();
        return new ResponseEntity<List<EmpresaResponse>>(entidades,HttpStatus.OK);
    }

    @GetMapping("/all/{nombre}")
    public ResponseEntity<List<EmpresaResponse>>listEntidadesNombre(@PathVariable String nombre){
        List<EmpresaResponse> entidades = empresaService.listEntidadNombre(nombre);
        return new ResponseEntity<List<EmpresaResponse>>(entidades,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> listEntidadesId(@PathVariable Long id) {
        EmpresaResponse er = empresaService.listEntidadId(id);
        return new ResponseEntity<>(er, HttpStatus.OK);
    }

    @GetMapping("entidadR/{id}")
    public ResponseEntity<RepresentanteResponse> repre(@PathVariable Long id) {
        RepresentanteResponse r= empresaService.getRepresentante(id);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntidad(@PathVariable Long id){
        empresaService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Empresa eliminada"),HttpStatus.OK);
    }
}
