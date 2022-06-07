package com.tecazuay.complexivog2c2.model.Primary.proyecto;


import com.tecazuay.complexivog2c2.model.Primary.Anexos.*;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmp;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "proyecto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoPPP implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String nombre;

    private String lineaaccion;

    private String codigocarrera;

    private boolean estado;

    private int horas;

    private Long entidadbeneficiaria;

    @Temporal(TemporalType.DATE)
    private Date fechaat;

    @Column(name = "objetivo_general")
    private String objetivoGeneral;

    @Column(name = "alcance_territorial")
    private String alcanceTerritorial;

    @Column(name = "programa_vinculacion")
    private String programaVinculacion;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_director", referencedColumnName = "id")
    private TutorEmp tutorEmp;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_responsable", referencedColumnName = "id")
    private ResponsablePPP responsablePPP;

    @OneToMany(targetEntity = Anexo1.class, mappedBy = "proyectoPPP")
    private List<Anexo1> anexo1;

    @OneToMany(targetEntity = ActividadesProyecto.class, mappedBy = "proyectoPPP", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ActividadesProyecto> actividadesProyecto;

    @OneToMany(targetEntity = RequisitosProyecto.class, mappedBy = "proyectoPPP")
    private List<RequisitosProyecto> requisitosProyecto;

    @OneToMany(targetEntity = ObjetivosEspecificosProyecto.class, mappedBy = "proyectoPPP", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ObjetivosEspecificosProyecto> objetivosEspecificosProyecto;

    @OneToMany(mappedBy = "proyectoPPP")
    private List<TutorAcademicoDelegados> tutorAcademicoDelegados = new ArrayList<>();



    @Column(name = "plazo_ejecucion")
    private String plazoEjecucion;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    private int participantes;

    @PostLoad
    private void verifyEndDate() {
        if (this.fechaFin.before(new Date())) {
            this.estado = false;
            this.tutorAcademicoDelegados.forEach(d -> d.setEstado(false));
            this.tutorEmp.setEstado(false);
        }
    }
}
