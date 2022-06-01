package com.tecazuay.complexivog2c2.dto.docentes;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocenteRequest implements Serializable {

    private String cedula;
    private String cargo;
    private boolean estado;

}
