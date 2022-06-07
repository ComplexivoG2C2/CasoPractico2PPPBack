package com.tecazuay.complexivog2c2.model.Secondary.docentes;

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
@Table(name = "vdocentespppdbyid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VDocentesByIdppp implements Serializable {
    @Id
    @Column(name = "id_docente", nullable = false,updatable = false)
    private Long id_persona;

    @Column(name="docente_codigo")
    private String cedula;



    private String docente_tipo_tiempo;

    private String docente_titulo;

}
