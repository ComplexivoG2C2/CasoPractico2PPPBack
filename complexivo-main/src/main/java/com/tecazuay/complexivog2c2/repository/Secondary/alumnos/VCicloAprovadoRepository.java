package com.tecazuay.complexivog2c2.repository.Secondary.alumnos;


import com.tecazuay.complexivog2c2.model.Secondary.alumnos.VCicloAprobado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VCicloAprovadoRepository extends JpaRepository<VCicloAprobado,String > {
    Optional<VCicloAprobado> findByCedula(String cedula) ;
}
