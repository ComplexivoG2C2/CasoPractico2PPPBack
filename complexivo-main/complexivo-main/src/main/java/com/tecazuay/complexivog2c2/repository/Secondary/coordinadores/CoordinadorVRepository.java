package com.tecazuay.complexivog2c2.repository.Secondary.coordinadores;

import com.tecazuay.complexivog2c2.model.Secondary.coordinadores.VCoordinadoresppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordinadorVRepository extends JpaRepository<VCoordinadoresppp,String> {
    Boolean existsByCedula(String cedula);
    List<VCoordinadoresppp> findByCedula(String cedula);
}
