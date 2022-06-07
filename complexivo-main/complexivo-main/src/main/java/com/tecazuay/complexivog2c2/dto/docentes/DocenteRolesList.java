package com.tecazuay.complexivog2c2.dto.docentes;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocenteRolesList implements Serializable {

    private String coordinador_id;

    private List<DocenteRequest> docentes;

    private Long idProyecto;


}
