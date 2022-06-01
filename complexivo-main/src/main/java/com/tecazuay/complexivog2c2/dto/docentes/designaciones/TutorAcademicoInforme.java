package com.tecazuay.complexivog2c2.dto.docentes.designaciones;

import lombok.Data;

import java.io.Serializable;
@Data
public class TutorAcademicoInforme implements Serializable {
    private Long id;

    private String cedula;

    private String nombresCompletos;

    private String carrera;

}
