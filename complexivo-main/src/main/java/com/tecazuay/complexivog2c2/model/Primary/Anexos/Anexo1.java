package com.tecazuay.complexivog2c2.model.Primary.Anexos;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "anexo1")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anexo1 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_delegacion")
    private Date fechaDelegacion;

    @Column(name = "docente_titulo")
    private String docenteTitulo;

    @Column(name = "cedula_delegado")
    private String cedulaDelegado;

    @Column(name = "nombre_Delegado")
    private String nombreDelegado;

    @Column(name = "nombre_carrera")
    private String nombreCarrera;

    @Column(name = "nombre_rol")
    private String nombreRol;

    @Column(name = "nombre_proyecto")
    private String nombreProyecto;

    @Column(name = "cedula_coordinador")
    private String cedulaCoordinador;

    @Column(name = "nombre_coordinador")
    private String nombreCoordinador;

    @Column(name = "diglas_carrera")
    private String siglasCarrera;

    @Column(name = "fecha_delegado")
    private Date fechaDelegado;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "proyecto_id", referencedColumnName = "id")
    private ProyectoPPP proyectoPPP;

    @Column(length = 10485760)
    private String documento;

    @Column(name = "num_proceso")
    private Integer numProceso;

}
