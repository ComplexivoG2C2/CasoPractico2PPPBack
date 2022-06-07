package com.tecazuay.complexivog2c2.repository.Primary.Anexos;

import com.tecazuay.complexivog2c2.model.Primary.Anexos.Anexo1;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Anexo1Repository extends JpaRepository<Anexo1, Long> {

    List<Anexo1> findAllByProyectoPPP(ProyectoPPP proyectoPPP);

    List<Anexo1> findAllByCedulaDelegado(String cedulaDelegado);

    List<Anexo1> findAllBySiglasCarrera(String siglasCarrera);

    void deleteAllByProyectoPPP(ProyectoPPP proyectoPPP);

}
