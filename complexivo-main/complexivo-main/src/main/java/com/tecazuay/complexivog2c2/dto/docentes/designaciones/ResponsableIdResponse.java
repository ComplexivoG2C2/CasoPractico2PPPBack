package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.util.Date;

@Data
public class ResponsableIdResponse {
    private Long id;
    private String Cedula;
    private String nombres_completo;
    private String titulo;
    private String docente_tipo_tiempo;
    private Date fecha_inicio_periodo;
    private Date fecha_fin_periodo;
    private Date fecha_designacion;

}
