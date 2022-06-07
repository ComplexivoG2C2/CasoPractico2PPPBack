package com.tecazuay.complexivog2c2.controller;

import com.tecazuay.complexivog2c2.dto.carreras.CarrerasResponse;
import com.tecazuay.complexivog2c2.service.carreras.CarrerasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/carreras")
public class CarrerasController {

    @Autowired
    private CarrerasService carrerasService;

    @GetMapping
    public ResponseEntity<List<CarrerasResponse>> listCarre() {
        List<CarrerasResponse> list = carrerasService.listaCarreas();
        return new ResponseEntity<List<CarrerasResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/nombre/{codigo}")
    public ResponseEntity<CarrerasResponse> carreraCodigo(@PathVariable String codigo){
        CarrerasResponse c=carrerasService.CarreraByCodigo(codigo);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
