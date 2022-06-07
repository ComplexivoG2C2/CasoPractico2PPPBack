package com.tecazuay.complexivog2c2.model.Primary.coordinadores;

import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmp;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "coordinador")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinadorCarrera implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    @OneToMany(targetEntity = TutorAcademicoDelegados.class, mappedBy = "coordinadorCarrera")
    private List<TutorAcademicoDelegados> tutorAcademicoDelegados;

    @OneToMany(targetEntity = ResponsablePPP.class, mappedBy = "coordinadorCarrera")
    private List<ResponsablePPP> responsablePPP;

    @OneToMany(targetEntity = TutorEmp.class, mappedBy = "coordinadorCarrera")
    private List<TutorEmp> tutorEmp;

    //Llave Foranea
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;


}
