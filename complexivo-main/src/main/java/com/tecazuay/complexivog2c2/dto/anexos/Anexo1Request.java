package com.tecazuay.complexivog2c2.dto.anexos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Anexo1Request implements Serializable {

    private Long id;

    private Date fechaDelegacion;

    private String docenteTitulo;

    private String cedulaDelegado;

    private String nombreDelegado;

    private String nombreCarrera;

    private String nombreRol;

    private String nombreProyecto;

    private String cedulaCoordinador;

    private String nombreCoordinador;

    private String siglasCarrera;

    private Date fechaDelegado;

    private Long idProyectoPPP;

    private String documento;   //Byte[] a String

    private Integer numProceso;

}
