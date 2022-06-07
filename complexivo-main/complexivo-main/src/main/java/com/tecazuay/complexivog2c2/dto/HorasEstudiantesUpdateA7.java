package com.tecazuay.complexivog2c2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HorasEstudiantesUpdateA7 {
    private Long id;
    private String resultados;
    private String actividad;
    private String nombreEstudiante;
    private String cedulaEstudiante;
    private int numHoras;
    private Date fechaInicio;
    private Date fechaFin;
    private String observaciones;
}
