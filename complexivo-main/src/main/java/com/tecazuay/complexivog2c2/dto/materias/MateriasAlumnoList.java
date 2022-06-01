package com.tecazuay.complexivog2c2.dto.materias;

import lombok.Data;

import java.util.List;

@Data
public class MateriasAlumnoList {
    private String cedula;
    private List<MateriaNombre> materias;
}
