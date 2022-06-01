package com.tecazuay.complexivog2c2.model.Secondary.coordinadores;

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
@Table(name = "vcoordinadoresppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VCoordinadoresppp implements Serializable
{


    @Column(name="persona_identificacion")
    private String cedula;

    @Id
    @Column(name="carrera_codigo")
    private String carreraCodigo;


}
