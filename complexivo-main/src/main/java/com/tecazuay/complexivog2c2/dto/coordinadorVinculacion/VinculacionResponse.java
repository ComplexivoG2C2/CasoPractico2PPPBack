package com.tecazuay.complexivog2c2.dto.coordinadorVinculacion;

import java.io.Serializable;

public class VinculacionResponse implements Serializable {

    private String cedula;

    private String nombres;

    private String apellidos;

    private String titulo;

    private String carga;

    private boolean estado;

    public VinculacionResponse() {
    }

    public VinculacionResponse(String cedula, String nombres, String apellidos, String titulo, String carga, boolean estado) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.titulo = titulo;
        this.carga = carga;
        this.estado = estado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
