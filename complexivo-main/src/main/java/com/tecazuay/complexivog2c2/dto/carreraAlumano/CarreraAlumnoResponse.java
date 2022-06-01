package com.tecazuay.complexivog2c2.dto.carreraAlumano;

import lombok.Data;

import java.io.Serializable;
@Data
public class CarreraAlumnoResponse implements Serializable {
    private String cedula;

    private String codigoCarrera;
}
