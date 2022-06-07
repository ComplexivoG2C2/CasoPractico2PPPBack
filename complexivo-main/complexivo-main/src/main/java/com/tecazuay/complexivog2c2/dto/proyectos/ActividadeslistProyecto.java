package com.tecazuay.complexivog2c2.dto.proyectos;

import java.io.Serializable;

public class ActividadeslistProyecto implements Serializable {


    private String descripcion;


    public ActividadeslistProyecto() {
    }

    public ActividadeslistProyecto(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
