package com.tecazuay.complexivog2c2.dto.alumnos;

import lombok.Data;

@Data
public class AlumnosCiclosResponse {

    private String cedula;


    private String primerApellido;


    private String segundoApellido;


    private String primerNombre;


    private String segundoNombre;


    private String codigoCarrera;


    private String nombreCarrera;


    private int ciclo;


    private String paralelo;


    private String jornada;
}
