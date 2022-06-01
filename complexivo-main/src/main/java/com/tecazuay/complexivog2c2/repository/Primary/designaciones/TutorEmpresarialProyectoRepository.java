package com.tecazuay.complexivog2c2.repository.Primary.designaciones;


import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmpresarial;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TutorEmpresarialProyectoRepository extends JpaRepository<TutorEmpresarial, Long> {

    Optional<TutorEmpresarial> findByCedula(String cedula);

    List<TutorEmpresarial> findByCoordinadorCarrera(CoordinadorCarrera coordinadorCarrera);

    Optional<TutorEmpresarial> findByProyectoPPPAndEstado(ProyectoPPP proyectoPPP, boolean estado);

    Boolean existsByCedula(String cedula);

    Boolean existsByCedulaAndEstado(String cedula, Boolean estado);

    Boolean existsByProyectoPPP(ProyectoPPP proyectoppp);

    //boolean existsById(Long id);
}
