package com.tecazuay.complexivog2c2.dto.proyectos;

import com.tecazuay.complexivog2c2.dto.docentes.designaciones.TutorAcademicoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoResponse implements Serializable {

    private Long id;

    private String codigo;

    private int participantes;

    private String nombre;

    private String lineaaccion;

    private String carrera;

    private String codigocarrera;

    private boolean estado;

    private Date fechaat;

    private String nombreEnudad;

    private Long entidadbeneficiaria;

    private String nombredirector;

    private String nombreresponsable;

    private String objetivoGeneral;

    private String alcanceTerritorial;

    private String programaVinculacion;

    private List<ActividadeslistProyecto> actividadeslistProyectos;

    private List<RequisitoslistProyecto> requisitoslistProyectos;

    private List<TutorAcademicoResponse> tutorAcademicoResponse;

    private List<ObjetivosEspeciicoslistProyecto> objetivosEspecificosProyecto;

    private String plazoEjecucion;

    private Date fechaInicio;

    private Date fechaFin;

    private int horas;

}
