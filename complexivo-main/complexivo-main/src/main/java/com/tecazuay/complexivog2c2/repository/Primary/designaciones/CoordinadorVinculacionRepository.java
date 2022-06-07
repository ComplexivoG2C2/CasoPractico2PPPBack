package com.tecazuay.complexivog2c2.repository.Primary.designaciones;



import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinadorVinculacionRepository extends JpaRepository<CoordinadorVinculacion, Long> {

    Optional<CoordinadorVinculacion> findByCedula(String cedula);
    Boolean existsByCedula (String cedula);
    Optional<CoordinadorVinculacion> findByUsuario(Usuario usuario);
    Boolean existsByCedulaAndEstado (String cedula, Boolean estado);
    Optional<CoordinadorVinculacion> findByEstado(boolean estado);
}
