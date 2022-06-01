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
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vmateriasppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VMateriasppp implements Serializable {
    @Id
    @Column(name = "id_materia", nullable = false,updatable = false)
    private Long id_materia;

    @Column(name="materia_codigo")
    private String materiaCodigo;

    private String materia_nombre;

    @Column(name="carrera_codigo")
    private String carreraCodigo;

    @Column(name="docente_codigo")
    private String cedula;

}
