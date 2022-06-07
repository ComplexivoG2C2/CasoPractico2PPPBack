package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponsableResponse implements Serializable {

    private Long id;

    private String cedula;

    private String codigoCarrera;

    private boolean estado;

    private Date fecha_inicio_periodo;
    private Date fecha_fin_periodo;
    private Date fecha_designacion;
    private String usuario_id;
    private String coordinador_id;

}
