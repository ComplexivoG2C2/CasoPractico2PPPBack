package com.tecazuay.complexivog2c2.dto.empresa;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
public class EmpresaResponse implements Serializable {

    private Long id;

    private String nombre;

    private String representante;

    private String emailEmpresa;
    private String clave;
    private String token;

    private String emailRepresentante;

    private String telefonoEmpresa;

    private String celularRepresentante;

    private Date fechaCreacion;

    private String nombreCoordinador;

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

    private Long idCoordinador;



    public EmpresaResponse(Long id, String emailEmpresa, String clave) {
        this.id = id;
        this.emailEmpresa = emailEmpresa;
        this.clave = clave;
    }

    public EmpresaResponse(Long id, String emailEmpresa, String clave,  String token) {
        this.id = id;
        this.emailEmpresa = emailEmpresa;
        this.clave = clave;
        this.token = token;
    }



    }
