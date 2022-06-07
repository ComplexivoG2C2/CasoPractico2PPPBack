package com.tecazuay.complexivog2c2.dto.anexos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Anexo3Request implements Serializable {

    private Long id;
    private Date fecha_solicitud;
    private String titulo_responsable;
    private String nombre_responsable;
    private String siglas_carrera;
    private String nombresestudiante;
    private String apellidosestudiante;
    private String cedula;
    private String nombrecarrera;
    private String paralelo;
    private String jornada;
    private String nombreproyecto;
    private String documento;
    private Long idProyectoPPP;
    private String estado;
}
