package com.tecazuay.complexivog2c2.dto.carreras;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodigoCarrerasResponse implements Serializable {
    private String codigo;
}
