package com.tecazuay.complexivog2c2.repository.Primary.roles;


import com.tecazuay.complexivog2c2.model.Primary.roles.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByCodigo(String Codigo);
}
