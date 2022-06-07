package com.tecazuay.complexivog2c2.controller;


import com.tecazuay.complexivog2c2.dto.carreras.CodigoCarrerasResponse;
import com.tecazuay.complexivog2c2.dto.docentes.DocenteResponse;
import com.tecazuay.complexivog2c2.dto.docentes.designaciones.*;
import com.tecazuay.complexivog2c2.dto.docentes.DocenteRolesList;
import com.tecazuay.complexivog2c2.dto.docentes.DocentesMateriasList;
import com.tecazuay.complexivog2c2.exception.Mensaje;
import com.tecazuay.complexivog2c2.service.docentes.DocentesService;
import com.tecazuay.complexivog2c2.service.designaciones.TutorEmpService;
import com.tecazuay.complexivog2c2.service.designaciones.ResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/docentes")
public class DocentesRestController {

    @Autowired
    private DocentesService docentesService;

    @Autowired
    private TutorEmpService tutorEmpService;

    @Autowired
    private ResponsableService responsableService;

    @GetMapping("/{cedula}")
    public ResponseEntity<DocenteResponse> getById(@PathVariable String cedula) {
        DocenteResponse docenteResponse = docentesService.getByCedula(cedula);
        return new ResponseEntity<>(docenteResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DocentesMateriasList>> listDocentes() {
        List<DocentesMateriasList> docentes = docentesService.listaDocentes();
        return new ResponseEntity<List<DocentesMateriasList>>(docentes, HttpStatus.OK);
    }

    /**
     * Guardar docentes de apoyo
     *
     * @param docenteRolesList
     * @return
     */
    @PostMapping("/save/apoyo")
    public ResponseEntity<?> saveApoyo(@RequestBody DocenteRolesList docenteRolesList) {
        docentesService.saveRoles(docenteRolesList);
        return new ResponseEntity<>(new Mensaje("Docentes de Apoyo Guardados"), HttpStatus.CREATED);
    }

    @PostMapping("/save/director")
    public ResponseEntity<?> saveDirector(@RequestBody TutorEmpProyectoRequest tutorEmpProyectoRequest) {
        tutorEmpService.saveRolDirector(tutorEmpProyectoRequest);
        return new ResponseEntity<>(new Mensaje("Directo de Proyecto Guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/save/responsable")
    public ResponseEntity<?> saveResponsable(@RequestBody ResponsableRequest responsableRequest) {
        responsableService.saveRolResposable(responsableRequest);
        return new ResponseEntity<>(new Mensaje("Responsable de Practicas Pre Porfesionales Guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/update/responsable")
    public ResponseEntity<?> updateResponsable(@RequestBody ResponsableRequest responsableRequest) {
        responsableService.updateResponsable(responsableRequest);
        return new ResponseEntity<>(new Mensaje("Responsable de Practicas Pre Porfesionales Guardado"), HttpStatus.CREATED);
    }

    @GetMapping("/all/responsable")
    public ResponseEntity<List<ResponsableResponse>> lista() {
        return new ResponseEntity<List<ResponsableResponse>>(responsableService.lista(), HttpStatus.OK);
    }

    @GetMapping("/carreras/{cedulaCC}")
    public ResponseEntity<List<CodigoCarrerasResponse>> lista(@PathVariable String cedulaCC) {
        return new ResponseEntity<List<CodigoCarrerasResponse>>(docentesService.enviarSiglasCarrera(cedulaCC), HttpStatus.OK);
    }

    @GetMapping("/responsable/{codigoCarrera}")
    public ResponseEntity<?> responsable(@PathVariable String codigoCarrera) {
        ResponsableIdResponse response = docentesService.getByCodigoCarrera(codigoCarrera);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/director/{idProyecto}")
    public ResponseEntity<?> directorDatos(@PathVariable Long idProyecto) {
        TutorEmpProyectoResponse response = tutorEmpService.getDirector(idProyecto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/responsablePPP/{cedula}")
    public ResponseEntity<?> responsableCarrera(@PathVariable String cedula) {
        CodigoCarrerasResponse response = responsableService.codigoCarrera(cedula);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/director/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        tutorEmpService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Director de proyecto eliminado"), HttpStatus.OK);
    }

    @GetMapping("/proyecto/{id}/director/cedula")
    public ResponseEntity<?> getCedulaDirectorByIdProyecto(@PathVariable Long id) {
        return new ResponseEntity<>(tutorEmpService.getCedulaDirectorByProject(id), HttpStatus.OK);
    }

    @GetMapping("/{cedula}/carreras")
    public ResponseEntity<?> carrerasPorCedulaDocente(@PathVariable String cedula) {
        return new ResponseEntity<>(docentesService.carrerasPorCedula(cedula), HttpStatus.OK);
    }
}
