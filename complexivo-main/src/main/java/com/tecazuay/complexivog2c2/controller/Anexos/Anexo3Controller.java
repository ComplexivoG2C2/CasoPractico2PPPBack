package com.tecazuay.complexivog2c2.controller.Anexos;

import com.tecazuay.complexivog2c2.dto.alumnos.AlumnosCiclosResponse;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo3Request;
import com.tecazuay.complexivog2c2.dto.anexos.Anexo3Response;
import com.tecazuay.complexivog2c2.exception.Mensaje;
import com.tecazuay.complexivog2c2.service.Anexos.Anexo3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/anexo3")
public class Anexo3Controller {

    @Autowired
    private Anexo3Service anexo3Service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Anexo3Request anexo3Request){
        anexo3Service.save(anexo3Request);
        return new ResponseEntity(new Mensaje("Anexo 3 Creado"), HttpStatus.CREATED);

    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Anexo3Request anexo3Request){
        anexo3Service.update(anexo3Request);
        return new ResponseEntity(new Mensaje("Anexo 3 Actualiazado"), HttpStatus.CREATED);

    }

    @GetMapping("/allByProyecto/{idProyecto}")
    public ResponseEntity<List<Anexo3Response>> listAnexosPro(@PathVariable Long idProyecto) {
        List<Anexo3Response> anexos= anexo3Service.listAnexoProyecto(idProyecto);
        return  new ResponseEntity<List<Anexo3Response>>(anexos,HttpStatus.OK);
    }

    @GetMapping("/allByCedula/{cedula}")
    public ResponseEntity<List<Anexo3Response>>listAnexos(@PathVariable String cedula){
        List<Anexo3Response> anexos= anexo3Service.listAnexo(cedula);
        return  new ResponseEntity<List<Anexo3Response>>(anexos,HttpStatus.OK);
    }

    @GetMapping("datosAlumno/{cedula}")
    public ResponseEntity<?>alumno(@PathVariable String cedula){
        AlumnosCiclosResponse vista = anexo3Service.getDatosAlumno(cedula);
        return new ResponseEntity<>(vista,HttpStatus.OK);
    }

    @GetMapping("/allByCodigoCarrera/{codigoCarrera}")
    public ResponseEntity<List<Anexo3Response>>listAnexos3Carera(@PathVariable String codigoCarrera){
        List<Anexo3Response> anexos= anexo3Service.listAnexo3Carrera(codigoCarrera);
        return  new ResponseEntity<List<Anexo3Response>>(anexos,HttpStatus.OK);
    }
}
