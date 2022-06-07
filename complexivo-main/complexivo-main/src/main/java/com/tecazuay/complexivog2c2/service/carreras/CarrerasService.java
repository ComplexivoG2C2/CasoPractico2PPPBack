package com.tecazuay.complexivog2c2.service.carreras;

import com.tecazuay.complexivog2c2.dto.carreras.CarrerasResponse;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Secondary.carreras.VCarrerasAllppp;
import com.tecazuay.complexivog2c2.repository.Secondary.carreras.CarrerasAllRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrerasService {

    @Autowired
    CarrerasAllRepository carrerasAllRepository;

    /**
     * Mostramos todas las carreras segun la base Fénix
     * @return Nombre y codigo de C/Carrera
     */
    public List<CarrerasResponse> listaCarreas(){
        return carrerasAllRepository.findAll().stream().map(vCarerrasAllppp -> {
            CarrerasResponse nombre= new CarrerasResponse();
            nombre.setNombre(vCarerrasAllppp.getCarrera_nombre());
            nombre.setCodigo(vCarerrasAllppp.getCarreraCodigo());
            return nombre;
        }).collect(Collectors.toList());
    }

    public CarrerasResponse CarreraByCodigo(String codigo){
        Optional<VCarrerasAllppp> optional = carrerasAllRepository.findByCarreraCodigo(codigo);
        if(optional.isPresent()){
            CarrerasResponse c = new CarrerasResponse();
            c.setNombre(optional.get().getCarrera_nombre());
            return c;
        }
        throw new ResponseNotFoundException("Carrera", "Código", codigo);
    }


}
