package com.tecazuay.complexivog2c2.model.Primary.desigaciones;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tutores_academicos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorAcademicoDelegados implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinador_id", referencedColumnName = "id")
    private CoordinadorCarrera coordinadorCarrera;

    //Llave Foranea
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="proyecto_id",referencedColumnName = "id")
    private ProyectoPPP proyectoPPP;

    private String cedula;

    @Temporal(TemporalType.DATE)

    private Date fecha_designacion;


    @PrePersist
    public void crear_fecha() {
        this.fecha_designacion = new Date();
    }


}
