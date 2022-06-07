package com.tecazuay.complexivog2c2.dto.coordinadorVinculacion;

import java.io.Serializable;

public class VinculacionRequest implements Serializable {

    private String cedula;

    private boolean estado;

    public VinculacionRequest() {
    }

    public VinculacionRequest(String cedula, boolean estado) {
        this.cedula = cedula;
        this.estado = estado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
