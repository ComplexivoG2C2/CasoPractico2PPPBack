package com.tecazuay.complexivog2c2.repository.Primary.autoridades;

import com.tecazuay.complexivog2c2.model.Primary.autoridades.Autoridades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoridadesRepository extends JpaRepository<Autoridades, Long> {

    Boolean existsByCedula(String cedula);
    Optional<Autoridades>findByCedula(String cedula);
    Boolean existsByCedulaAndEstado (String cedula,Boolean estado);
}
