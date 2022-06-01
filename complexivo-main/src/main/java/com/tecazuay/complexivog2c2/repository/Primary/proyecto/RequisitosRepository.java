package com.tecazuay.complexivog2c2.repository.Primary.proyecto;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.RequisitosProyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequisitosRepository extends JpaRepository<RequisitosProyecto,Long> {

    @Override
    Optional<RequisitosProyecto> findById(Long id);

    List<RequisitosProyecto> findAllByProyectoPPP(ProyectoPPP proyectoPPP);

}
