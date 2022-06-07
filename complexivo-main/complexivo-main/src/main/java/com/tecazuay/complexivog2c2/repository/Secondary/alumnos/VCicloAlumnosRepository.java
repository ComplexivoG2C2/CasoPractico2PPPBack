package com.tecazuay.complexivog2c2.repository.Secondary.alumnos;

import com.tecazuay.complexivog2c2.model.Secondary.alumnos.VCicloAlumnos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VCicloAlumnosRepository extends JpaRepository<VCicloAlumnos,String > {

    Optional<VCicloAlumnos> findFirstByCedulaOrderByCicloDesc(String cedula);

}
