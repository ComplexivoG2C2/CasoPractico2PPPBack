package com.tecazuay.complexivog2c2.repository.Primary.usuario;

import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCedula(String cedula);

    Boolean existsByEmail(String email);
    Boolean existsByCedula(String cedula);


}