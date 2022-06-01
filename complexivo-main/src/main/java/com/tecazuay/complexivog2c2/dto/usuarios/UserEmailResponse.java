package com.tecazuay.complexivog2c2.dto.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserEmailResponse implements Serializable {
    private String email;
}
