package com.miproyecto.gamestack.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarCuentaData {
    @Email
    String email;
    String codigoActivacion;
}
