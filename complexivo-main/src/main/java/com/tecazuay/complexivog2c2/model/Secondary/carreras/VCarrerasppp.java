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
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vcarrerasppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VCarrerasppp  implements Serializable {

    @Id
    @Column(name = "id_carrera", nullable = false,updatable = false)
    private Long id_carrera;

    @Column(name="carrera_nombre")
    private String nombre_carrera;

    @Column(name="carrera_codigo")

    private String carreraCodigo;

    @Column(name="docente_codigo")
    private String cedula;


}
