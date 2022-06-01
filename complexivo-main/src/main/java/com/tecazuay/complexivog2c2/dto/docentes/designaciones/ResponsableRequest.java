package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponsableRequest implements Serializable {

    private Long id;

    private String coordinador_id;

    private String cedula;

    private Date fecha_inicio_periodo;
    private Date fecha_fin_periodo;
    private Date fecha_designacion;

    private boolean estado;

    private String codigoCarrera;
    private String usuario_id;
}
