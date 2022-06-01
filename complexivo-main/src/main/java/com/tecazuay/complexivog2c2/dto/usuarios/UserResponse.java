package com.tecazuay.complexivog2c2.dto.usuarios;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//GENERAL--> SE COMUNICA CON EL MODELO
@Getter
@Setter
@AllArgsConstructor
public class UserResponse implements Serializable {

    private Long id;

    private String email;

    private String urlFoto;

    private String nombrescompletos;

    private String cedula;

    private String rol;

    private String token;

    public UserResponse(Long id, String email, String urlFoto, String nombrescompletos, String cedula, String rol) {
        this.id = id;
        this.email = email;
        this.urlFoto = urlFoto;
        this.nombrescompletos = nombrescompletos;
        this.cedula = cedula;
        this.rol = rol;
    }
}
