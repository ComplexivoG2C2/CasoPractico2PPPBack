package com.tecazuay.complexivog2c2.repository.Secondary.materias;

import com.tecazuay.complexivog2c2.model.Secondary.materias.VMateriasAlumnos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VMateriasAlumnosRepository extends JpaRepository<VMateriasAlumnos, String> {
    List<VMateriasAlumnos> findAllByCedula(String cedula);
}