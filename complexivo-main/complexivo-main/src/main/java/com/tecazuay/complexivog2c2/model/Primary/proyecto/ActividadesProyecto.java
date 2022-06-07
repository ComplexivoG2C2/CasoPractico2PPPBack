package com.tecazuay.complexivog2c2.model.Primary.proyecto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "actividadesproyecto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActividadesProyecto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="idproyecto", referencedColumnName = "id")
    private ProyectoPPP proyectoPPP;





}