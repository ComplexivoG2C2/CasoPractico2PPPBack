package com.tecazuay.complexivog2c2.dto.docentes;

import lombok.Data;


import java.io.Serializable;

@Data
public class DocentesList implements Serializable {

    private Long id_persona;

    private String cedula;

    private String docente_tipo_tiempo;

    private String carrera_nombre;

    private String materia_nombre;

    private String nombres;

    private String apellidos;

}
