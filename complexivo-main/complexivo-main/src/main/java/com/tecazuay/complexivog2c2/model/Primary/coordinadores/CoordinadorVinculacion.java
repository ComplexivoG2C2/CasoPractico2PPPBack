package com.tecazuay.complexivog2c2.model.Primary.coordinadores;

import com.tecazuay.complexivog2c2.model.Primary.empresa.Empresa;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name="coordinador_vinculacion")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinadorVinculacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    private boolean estado;

    @OneToMany(targetEntity = Empresa.class,mappedBy = "coordinadorVinculacion")
    private List<Empresa> empresa;

    //Llave Foranea
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;


}
