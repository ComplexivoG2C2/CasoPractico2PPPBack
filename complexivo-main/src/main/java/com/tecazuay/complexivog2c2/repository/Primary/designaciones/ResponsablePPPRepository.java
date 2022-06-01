package com.tecazuay.complexivog2c2.repository.Primary.designaciones;


import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsablePPPRepository extends JpaRepository<ResponsablePPP, Long> {

    Optional <ResponsablePPP> findByCedulaAndEstado(String cedula,Boolean estado);
    Boolean existsByEstadoAndCodigoCarrera(Boolean estado, String codigoCarrera);
    Boolean existsByCedulaAndEstado (String cedula,Boolean estado);
    Optional <ResponsablePPP> findByCedula(String cedula);
    Boolean existsByCedula(String cedula);
    Optional <ResponsablePPP> findByEstadoAndCodigoCarrera(Boolean estado, String codigoCarrera);
}
