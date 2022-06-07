package com.tecazuay.complexivog2c2.model.Primary.Anexos;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "anexo3")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Anexo3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;
    @Column(name = "docente_titulo")
    private String docenteTitulo;
    @Column(name = "nombre_responsable")
    private String nombreReponsable;
    @Column(name = "siglas_carrera")
    private String siglasCarrera;
    @Column(name = "nombre_estudiante")
    private String nombreEstudiante;
    @Column(name = "apellido_estudiante")
    private String apellidoEstudiante;
    @Column(name = "cedula")
    private String cedulaEstudiante;
    @Column(name = "nombrecarrerra")
    private String nombreCarrera;
    @Column(name = "paralelo")
    private String paralelo;
    @Column(name = "jornada")
    private String jornada;
    @Column(name = "nombre_proyecto")
    private String nombreProyecto;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id")
    private ProyectoPPP proyectoPPP;

    @Column(length = 10485760)
    private String documento;

    private String estado;
}
