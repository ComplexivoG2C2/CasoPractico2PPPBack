package com.tecazuay.complexivog2c2.repository.Secondary.carreras;

import com.tecazuay.complexivog2c2.model.Secondary.carreras.VCarrerasDocentesPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VCarrerasDocentesRepository extends JpaRepository<VCarrerasDocentesPPP, String> {
    List<VCarrerasDocentesPPP> findAllByCedula(String cedula);
}
