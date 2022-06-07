package com.tecazuay.complexivog2c2.dto.usuarios;

import java.io.Serializable;

///GUARDAR
public class RegisterRequest implements Serializable {

    private String email;

    private String urlFoto;

    private String cedula;

    private String rol;


    public RegisterRequest(String email, String urlFoto, String cedula, String rol) {
        this.email = email;
        this.urlFoto = urlFoto;
        this.cedula = cedula;
        this.rol = rol;
    }

    public RegisterRequest(String email, String urlFoto, String cedula) {
        this.email = email;
        this.urlFoto = urlFoto;
        this.cedula = cedula;
    }

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
