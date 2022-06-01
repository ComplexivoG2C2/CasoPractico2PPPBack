package com.tecazuay.complexivog2c2.model.Primary.empresa;

import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
//hola un ejemplo aqui
@Table(name="empresa")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Comentario prueba
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String representante;

    @Column(name="email_entidad")
    private String emailEntidad;

    @Column(name="email_representante")
    private String emailRepresentante;

    @Column(name="telefono_entidad")
    private String telefonoEntidad;

    @Column(name="celular_representante")
    private String celularRepresentante;

    @Column(name="fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;


    private String ciudad;

    private String direccion;

    @Column(name="nombre_administrador")
    private String nombreAdministrador;

    @Column(name="cedula_administrador")
    private String cedulaAdministrador;

    @Column(name="correo_administrador")
    private String correoAdministrador;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="coordinadorV_id", referencedColumnName = "id")
    private CoordinadorVinculacion coordinadorVinculacion;


    @Column(name="descripcion_empresa",length = 10485760)
    private String descripcionEmpresa;


}
