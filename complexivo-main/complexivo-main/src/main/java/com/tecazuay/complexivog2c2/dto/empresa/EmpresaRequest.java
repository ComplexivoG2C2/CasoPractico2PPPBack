package com.tecazuay.complexivog2c2.dto.empresa;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EmpresaRequest implements Serializable {

    private Long id;

    private String nombre;

    private String representante;


    private String emailEmpresa;
    private String clave;

    private String emailRepresentante;


    private String telefonoEmpresa;


    private String celularRepresentante;


    private Date fechaCreacion;


    private Long idCoordinador;

    private String ciudad;

    private String direccion;


//    private String nombreAdministrador;
//
//
//    private String cedulaAdministrador;
//
//
//    private String correoAdministrador;

    private String descripcionEmpresa;



    public EmpresaRequest() {
    }

    public EmpresaRequest(String emailEmpresa, String clave) {
        this.emailEmpresa = emailEmpresa;
        this.clave = clave;
    }

}
