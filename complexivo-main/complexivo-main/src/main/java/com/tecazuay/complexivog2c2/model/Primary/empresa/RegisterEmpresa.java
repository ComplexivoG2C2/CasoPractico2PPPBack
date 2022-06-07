package com.tecazuay.complexivog2c2.model.Primary.empresa;

import lombok.Data;

import java.io.Serializable;
@Data
public class RegisterEmpresa implements Serializable {
    private String emailEmpresa;

    private String clave;
    private String username;

    public RegisterEmpresa(String emailEmpresa,  String clave, String username) {
        this.emailEmpresa = emailEmpresa;
        this.clave = clave;
        this.username = username;

    }

    public RegisterEmpresa() {
    }
}
