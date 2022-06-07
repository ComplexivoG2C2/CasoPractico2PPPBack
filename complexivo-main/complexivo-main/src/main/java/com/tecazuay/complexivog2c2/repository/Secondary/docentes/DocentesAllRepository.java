package com.tecazuay.complexivog2c2.repository.Secondary.docentes;

import com.tecazuay.complexivog2c2.model.Secondary.docentes.VDocentespppall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocentesAllRepository extends JpaRepository<VDocentespppall, Long> {
    @Override
    List<VDocentespppall> findAll();
    Boolean existsByCedula(String cedula);
    Optional<VDocentespppall> findByCedula(String cedula);
}
