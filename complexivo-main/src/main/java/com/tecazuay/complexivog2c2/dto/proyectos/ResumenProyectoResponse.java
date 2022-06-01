package com.tecazuay.complexivog2c2.dto.proyectos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResumenProyectoResponse {

    private Long id;
    private String nombre;
    private boolean estado;
    private Date fechaInicio;
    private Date fechaFin;

    private List<Long> anexos1;
    private List<Long> anexos2;
    private List<Long> anexos3;
    private List<Long> anexos4;
    private List<Long> anexos5;
    private List<Long> anexos6;
    private List<Long> anexos6_1;
    private List<Long> anexos6_2;
    private List<Long> anexos7;
    private List<Long> anexos8;
    private List<Long> anexos9;
    private List<Long> anexos10;
    private List<Long> anexos11;
    private List<Long> anexos12;
    private List<Long> anexos13;
    private List<Long> informeInicial;
    private List<Long> informeSeguimiento;
    private List<Long> informeFinal;

}
