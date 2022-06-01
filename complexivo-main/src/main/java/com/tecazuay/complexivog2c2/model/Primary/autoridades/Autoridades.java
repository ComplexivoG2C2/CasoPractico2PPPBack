package com.tecazuay.complexivog2c2.model.Primary.autoridades;

import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="autoridades")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autoridades implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    private Boolean estado;


    @Column(name="fecha_creacion")
    private Date fechaCreacion;

    @PrePersist
    public void crearFecha(){
        fechaCreacion=new Date();
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;


}
