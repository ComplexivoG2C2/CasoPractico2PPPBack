package com.tecazuay.complexivog2c2.model.Primary.usuario;

import com.tecazuay.complexivog2c2.model.Primary.alumnos.Alumnos;
import com.tecazuay.complexivog2c2.model.Primary.autoridades.Autoridades;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmp;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.roles.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;



    private String urlFoto;

    private String cedula;

    @OneToOne(mappedBy = "usuario")
    private Autoridades autoridades;

    @OneToOne(mappedBy = "usuario")
    private CoordinadorCarrera coordinadorCarrera;

    @OneToOne(mappedBy = "usuario")
    private CoordinadorVinculacion coordinadorVinculacion;

    @OneToOne(mappedBy = "usuario")
    private TutorEmp tutorEmp;

    @OneToOne(mappedBy = "usuario")
    private TutorAcademicoDelegados tutorAcademicoDelegados;

    @OneToOne(mappedBy = "usuario")
    private ResponsablePPP responsablePPP;

    @OneToOne(mappedBy = "usuario")
    private Alumnos alumnos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rol_id", referencedColumnName = "id")
    private Roles roles;

}