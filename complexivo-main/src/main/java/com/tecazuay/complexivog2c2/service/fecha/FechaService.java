package com.tecazuay.complexivog2c2.service.fecha;

import com.tecazuay.complexivog2c2.dto.fecha.FechaResponse;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


@Service
public class FechaService {

    public FechaResponse obtenerHoraFecha(){
        FechaResponse f= new FechaResponse();
        f.setFecha(LocalDateTime.now());
        return f;
    }
}
