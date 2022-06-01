package com.tecazuay.complexivog2c2.model.Secondary.materias;

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
@Table(name = "vmateriasalumnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VMateriasAlumnos {

    @Column(name = "persona_identificacion")
    private String cedula;

    @Column(name = "id_alumno")
    private int idAlumno;

    private String apellidos;

    private String nombres;

    @Id
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "curso_nombre")
    private String cursoNombre;

    @Column(name = "materia_nombre")
    private String materiaNombre;

    private String estado;
}
