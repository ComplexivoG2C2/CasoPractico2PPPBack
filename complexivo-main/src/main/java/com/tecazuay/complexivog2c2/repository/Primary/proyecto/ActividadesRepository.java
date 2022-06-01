package com.tecazuay.complexivog2c2.repository.Primary.proyecto;

import com.tecazuay.complexivog2c2.model.Primary.proyecto.ActividadesProyecto;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadesRepository extends JpaRepository<ActividadesProyecto,Long> {

    @Override
    List<ActividadesProyecto> findAll();

    List<ActividadesProyecto> findAllByProyectoPPP(ProyectoPPP proyectoPPP);

}
