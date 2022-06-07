package com.tecazuay.complexivog2c2.repository.Secondary.carreras;

import com.tecazuay.complexivog2c2.model.Secondary.carreras.VCarrerasppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarrerasRepository extends JpaRepository<VCarrerasppp, Long> {

    List<VCarrerasppp> findByCedula(String cedula);
    Optional<VCarrerasppp> findByCarreraCodigo(String codigo);
    Boolean existsByCarreraCodigo(String codigo);


}
