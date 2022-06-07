package com.tecazuay.complexivog2c2.model.Primary.roles;

import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name="roles")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String descripcion;

    @OneToMany(targetEntity = Usuario.class,mappedBy = "roles")
    private List<Usuario> usuario;


}
