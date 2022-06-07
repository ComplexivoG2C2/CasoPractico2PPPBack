package com.tecazuay.complexivog2c2.repository.Secondary.carreras;

import com.tecazuay.complexivog2c2.model.Secondary.carreras.VCarrerasAllppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrerasAllRepository extends JpaRepository<VCarrerasAllppp,Long> {
    boolean existsByCarreraCodigo (String codigo);
    Optional<VCarrerasAllppp> findByCarreraCodigo (String codigo);
}
