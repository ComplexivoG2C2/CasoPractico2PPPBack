package com.tecazuay.complexivog2c2.repository.Secondary.alumnos;

import com.tecazuay.complexivog2c2.model.Secondary.alumnos.VAlumnosppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VAlumnosRepository extends JpaRepository<VAlumnosppp,Long> {

    List<VAlumnosppp> findAllByCodigoCarrera(String codigoCarrera);

    Optional<VAlumnosppp> findByCedulaAndCodigoCarrera(String cedula,String codigoCarrera);

    Optional<VAlumnosppp> findByCedula(String cedula);

    Boolean existsByCedula(String cedula);
}
