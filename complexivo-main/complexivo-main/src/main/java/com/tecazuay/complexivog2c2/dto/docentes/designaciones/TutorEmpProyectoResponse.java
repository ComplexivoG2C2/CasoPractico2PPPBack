package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;

@Data
public class TutorEmpProyectoResponse implements Serializable {

    private String cedula;

    private String nombre;

    private String apellidos;

    private String correo;
}
