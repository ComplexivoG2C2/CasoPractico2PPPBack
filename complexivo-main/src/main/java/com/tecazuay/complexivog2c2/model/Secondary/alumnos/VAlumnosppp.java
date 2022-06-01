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
@Table(name = "valumnosppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VAlumnosppp implements Serializable {

    @Id
    @Column(name = "id_persona", nullable = false,updatable = false)
    private Long id_persona;

    @Column(name="alumno_codigo")
    private String cedula;

    @Column(name="carrera_codigo")
    private String codigoCarrera;


}
