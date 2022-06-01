package com.tecazuay.complexivog2c2.repository.Secondary.materias;

import com.tecazuay.complexivog2c2.model.Secondary.materias.VMateriasppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MateriasRepository extends JpaRepository<VMateriasppp, Long> {
    List<VMateriasppp> findByCedula(String cedula);
    List<VMateriasppp> findAllByCarreraCodigo(String carreraCodigo);
}
