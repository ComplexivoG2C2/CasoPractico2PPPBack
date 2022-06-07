package com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera;


import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CoordinadorRepository extends JpaRepository<CoordinadorCarrera, Long> {

    Optional<CoordinadorCarrera> findByCedula(String cedula);
    Boolean existsByCedula (String cedula);

}
