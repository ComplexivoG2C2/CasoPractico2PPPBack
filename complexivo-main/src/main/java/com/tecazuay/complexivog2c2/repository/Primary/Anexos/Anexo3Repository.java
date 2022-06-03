package com.tecazuay.complexivog2c2.repository.Primary.Anexos;

import com.tecazuay.complexivog2c2.model.Primary.Anexos.Anexo3;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Anexo3Repository extends JpaRepository<Anexo3,Long> {
    List<Anexo3> findAllByProyectoPPP(ProyectoPPP proyectoPPP);
    List<Anexo3>findAllByCedulaEstudiante(String cedulaEstudiante);
    Optional<Anexo3> findByCedulaEstudianteAndProyectoPPP(String cedulaEstudiante,ProyectoPPP proyectoPPP);

}
