package com.tecazuay.complexivog2c2.repository.Primary.designaciones;


import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TutorAcademicoRepository extends JpaRepository<TutorAcademicoDelegados, Long> {

    Optional<TutorAcademicoDelegados> findByCedula(String cedula);
    List<TutorAcademicoDelegados> findByCoordinadorCarrera(CoordinadorCarrera coordinadorCarrera);
    Boolean existsByCedula (String cedula);
    Boolean existsByCedulaAndEstado (String cedula, Boolean estado);
    Boolean existsByProyectoPPP(ProyectoPPP proyectoPPP);
    Boolean existsByProyectoPPPAndCedula(ProyectoPPP proyectoPPP, String cedula);
    List<TutorAcademicoDelegados> findAllByProyectoPPP (ProyectoPPP proyectoPPP);
    List<TutorAcademicoDelegados> findByProyectoPPP (ProyectoPPP proyectoPPP);
    List<TutorAcademicoDelegados> findAllByCedula(String cedula);
}
