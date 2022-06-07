package com.tecazuay.complexivog2c2.repository.Primary.empresa;


import com.tecazuay.complexivog2c2.model.Primary.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findByNombreLikeIgnoreCase(String nombre);

    Optional<Empresa> findByEmailEmpresa(String emailEmpresa);
    Boolean existsByEmailEmpresa(String emailEmpresa);

}
