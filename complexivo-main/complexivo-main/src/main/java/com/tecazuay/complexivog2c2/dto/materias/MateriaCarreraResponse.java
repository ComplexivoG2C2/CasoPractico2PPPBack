package com.tecazuay.complexivog2c2.dto.materias;

import lombok.Data;

import java.io.Serializable;

@Data
public class MateriaCarreraResponse implements Serializable {

    private Long id;
    private String codigo;
    private String nombre;

}
