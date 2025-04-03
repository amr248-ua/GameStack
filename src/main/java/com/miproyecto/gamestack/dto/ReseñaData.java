package com.miproyecto.gamestack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReseñaData {
    private Long id;
    @Size(max = 2000, message = "Máximo 2000 carácteres")
    private String contenido;
    @NotNull(message = "Debes indicar si recomiendas o no el videojuego")
    private Boolean recomienda;
    private Boolean activo;
    private String fechaCreacion;

}
