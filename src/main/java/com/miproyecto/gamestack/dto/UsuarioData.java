package com.miproyecto.gamestack.dto;

import com.miproyecto.gamestack.validation.MaxByteSize;
import jakarta.validation.constraints.Size;
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
    private String codigoActivacion;
    private Boolean activo;
    private LocalDate fechaNacimiento;

    @MaxByteSize(value = 1_000_000, message = "El tamaño de la foto es demasiado grande")
    private byte[] fotoPerfil;
    @Size(max = 1000, message = "La biografía no puede tener más de 1000 caracteres")
    private String biografia;
}
