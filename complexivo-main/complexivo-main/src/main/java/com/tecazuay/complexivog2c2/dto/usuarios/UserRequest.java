package com.tecazuay.complexivog2c2.dto.usuarios;

import lombok.Data;

import java.io.Serializable;

//Si existe cambio
@Data
public class UserRequest implements Serializable {

    private String email;

    private String urlFoto;

    private String rol;

    public UserRequest() {
    }

    public UserRequest(String email, String urlFoto) {
        this.email = email;
        this.urlFoto = urlFoto;
    }

    public UserRequest(String email, String urlFoto, String rol) {
        this.email = email;
        this.urlFoto = urlFoto;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
