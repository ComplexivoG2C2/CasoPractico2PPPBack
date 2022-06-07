package com.tecazuay.complexivog2c2.dto.docentes;

import java.io.Serializable;

public class DocentesCarrerasList implements Serializable {

    private String nombrecarrras;

    public DocentesCarrerasList() {
    }

    public String getNombrecarrras() {
        return nombrecarrras;
    }

    public void setNombrecarrras(String nombrecarrras) {
        this.nombrecarrras = nombrecarrras;
    }
}
