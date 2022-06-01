package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TutorAcademicoResponse implements Serializable {
    private Long id;
    private Boolean estado;
    private String cedula;
    private Date fechaDesignacion;
    private String correo;
    private String nombres;
}
