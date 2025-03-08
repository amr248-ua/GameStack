package com.miproyecto.gamestack.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioData {
    private Long id;
    private String email;
    private String password;
    private String username;
    private LocalDate fechaNacimiento;
}
