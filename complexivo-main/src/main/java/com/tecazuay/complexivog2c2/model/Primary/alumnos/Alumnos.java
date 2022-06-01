package com.tecazuay.complexivog2c2.model.Primary.alumnos;

import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="alumnos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
