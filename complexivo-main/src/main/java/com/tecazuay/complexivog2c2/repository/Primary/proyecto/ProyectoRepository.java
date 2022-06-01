package com.tecazuay.complexivog2c2.repository.Primary.proyecto;

import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmpresarial;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<ProyectoPPP, Long> {

    Optional<ProyectoPPP> findByTutorEmpresarial(TutorEmpresarial tutorEmpresarial);

    Optional<ProyectoPPP> findByCodigo(String codigo);

    Optional<ProyectoPPP> findByIdAndEstado(Long id, boolean estado);
}
