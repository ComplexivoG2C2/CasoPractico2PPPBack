package com.tecazuay.complexivog2c2.dto.fecha;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FechaResponse implements Serializable {

    private LocalDateTime fecha;
}
