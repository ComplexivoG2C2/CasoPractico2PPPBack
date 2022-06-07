package com.tecazuay.complexivog2c2.dto.docentes;

import java.io.Serializable;

public class MateriaNombre implements Serializable {


    private String nombre;

    public MateriaNombre() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
