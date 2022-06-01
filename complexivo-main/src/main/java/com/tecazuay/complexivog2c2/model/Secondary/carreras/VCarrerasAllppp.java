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
@Table(name = "vCarrerasAllppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VCarrerasAllppp {
    @Id
    @Column(name = "id_carrera", nullable = false,updatable = false)
    private Long id_carrera;

    @Column(name="carrera_codigo")
    private String carreraCodigo;
    private String carrera_nombre;
}
