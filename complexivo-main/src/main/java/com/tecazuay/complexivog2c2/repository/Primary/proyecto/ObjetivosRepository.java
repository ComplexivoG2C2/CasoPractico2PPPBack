package com.tecazuay.complexivog2c2.repository.Primary.proyecto;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ObjetivosEspecificosProyecto;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObjetivosRepository extends JpaRepository<ObjetivosEspecificosProyecto,Long> {

    Optional<ObjetivosEspecificosProyecto> findById(Long id);

    List<ObjetivosEspecificosProyecto> findAllByProyectoPPP(ProyectoPPP proyectoPPP);
}
