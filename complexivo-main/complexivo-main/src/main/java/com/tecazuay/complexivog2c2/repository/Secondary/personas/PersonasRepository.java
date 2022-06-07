package com.tecazuay.complexivog2c2.repository.Secondary.personas;

import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PersonasRepository extends JpaRepository<VPersonasppp, Long> {
    @Override
    List<VPersonasppp> findAll();
    Optional<VPersonasppp> findByCedula(String cedula);
    Boolean existsByCedula(String cedula);
}
