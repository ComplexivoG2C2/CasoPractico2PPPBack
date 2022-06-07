package com.tecazuay.complexivog2c2.repository.Secondary.materias;

import com.tecazuay.complexivog2c2.model.Secondary.materias.VMateriasAllppp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriasAllRepository extends JpaRepository<VMateriasAllppp, Long> {

    List<VMateriasAllppp> findAllByCarreraCodigo(String carreraCodigo);
}
