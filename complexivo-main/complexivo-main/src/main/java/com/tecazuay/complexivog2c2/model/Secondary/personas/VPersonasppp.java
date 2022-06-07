package com.tecazuay.complexivog2c2.model.Secondary.personas;

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
@Table(name = "vpersonasppp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VPersonasppp implements Serializable {

    @Id
    @Column(name = "id_persona", nullable = false,updatable = false)
    private Long id_persona;
    @Column(name="persona_identificacion")
    private String cedula;

    private String rol_nombre;

    private String nombres;

    private String apellidos;


}
