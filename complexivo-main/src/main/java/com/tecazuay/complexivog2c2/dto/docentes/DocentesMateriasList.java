package com.tecazuay.complexivog2c2.dto.docentes;

import lombok.Data;

import java.util.List;

@Data
public class DocentesMateriasList {

    private String cedula;
    private String nombres_completo;
    private String titulo;
    private String docente_tipo_tiempo;
    private List<MateriaNombre> materias;
    private List<CarreraNombre> carreas;

}
