package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;

@Data
public class TutorEmpProyectoRequest implements Serializable {

    private String coordinador_id;

    private String cedula;

    private Long idProyecto;

    private boolean estado;
}
