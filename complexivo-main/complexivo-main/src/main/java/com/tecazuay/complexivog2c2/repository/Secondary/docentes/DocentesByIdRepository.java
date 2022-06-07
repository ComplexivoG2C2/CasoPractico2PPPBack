package com.tecazuay.complexivog2c2.repository.Secondary.docentes;

import com.tecazuay.complexivog2c2.model.Secondary.docentes.VDocentesByIdppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocentesByIdRepository extends JpaRepository<VDocentesByIdppp,Long> {
    @Override
    List<VDocentesByIdppp> findAll();
    Boolean existsByCedula(String cedula);
    Optional<VDocentesByIdppp> findByCedula(String cedula);
}
