package com.miproyecto.gamestack.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistroData {
    @Email
    private String email;
    private String password;
    private String user;
    private LocalDate fechaNacimiento;

}
