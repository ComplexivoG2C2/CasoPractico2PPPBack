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
    public ResponseEntity<List<EmpresaResponse>>listEmpresas(){
        List<EmpresaResponse> entidades = empresaService.listEmpresa();
        return new ResponseEntity<List<EmpresaResponse>>(entidades,HttpStatus.OK);
    }

    @GetMapping("/all/{nombre}")
    public ResponseEntity<List<EmpresaResponse>>listEmpresasNombre(@PathVariable String nombre){
        List<EmpresaResponse> entidades = empresaService.listEmpresaNombre(nombre);
        return new ResponseEntity<List<EmpresaResponse>>(entidades,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> listEmpresasId(@PathVariable Long id) {
        EmpresaResponse er = empresaService.listEmpresaId(id);
        return new ResponseEntity<>(er, HttpStatus.OK);
    }

    @GetMapping("empresaR/{id}")
    public ResponseEntity<RepresentanteResponse> repre(@PathVariable Long id) {
        RepresentanteResponse r= empresaService.getRepresentante(id);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntidad(@PathVariable Long id){
        empresaService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Empresa eliminada"),HttpStatus.OK);

    }



    @PostMapping("/login")
    public ResponseEntity<EmpresaResponse> login(@RequestBody EmpresaRequest empRequest) throws Exception {
        EmpresaResponse empResponse = empresaService.login(empRequest);
        if (empResponse == null){
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<EmpresaResponse>(empResponse, HttpStatus.OK);
        }
    }

}
