package com.tecazuay.complexivog2c2.model.Secondary.alumnos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vcicloalumnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VCicloAlumnos implements Serializable {

    @Id
    @Column(name="alumno_codigo")
    private String cedula;

    @Column(name="persona_primer_apellido")
    private String primerApellido;

    @Column(name="persona_segundo_apellido")
    private String segundoApellido;

    @Column(name="persona_primer_nombre")
    private String primerNombre;

    @Column(name="persona_segundo_nombre")
    private String segundoNombre;

    @Column(name="carrera_codigo")
    private String codigoCarrera;

    @Column(name="carrera_nombre")
    private String nombreCarrera;

    @Column(name="ciclo")
    private int ciclo;

    @Column(name="curso_paralelo")
    private String paralelo;

    @Column(name="nombre_jornada")
    private String jornada;




}
