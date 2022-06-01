package com.tecazuay.complexivog2c2.repository.Primary.alumnos;

import com.tecazuay.complexivog2c2.model.Primary.alumnos.Alumnos;
import com.tecazuay.complexivog2c2.model.Secondary.alumnos.VCicloAprobado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnosRepository extends JpaRepository<Alumnos,Long> {
    Optional<Alumnos> findByCedula(String cedula);
    Boolean existsByCedula (String cedula);
    Optional<VCicloAprobado> findAllByCedula(String cedula);
}
