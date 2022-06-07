package com.tecazuay.complexivog2c2.model.Secondary.carreras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "vcarrerasdocentesppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VCarrerasDocentesPPP {

    @Id
    @Column(name = "carrera_codigo")
    private String codigoCarrera;

    @Column(name = "docente_codigo", nullable = false,updatable = false)
    private String cedula;

}
