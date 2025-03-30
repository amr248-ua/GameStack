package com.miproyecto.gamestack.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class VideojuegoData {
    private Long id;
    private String titulo;
    private String sinopsis;
    private String imagen;
    private LocalDate fechaLanzamiento;
    private float puntuacionPromedio;

    private Set<String> plataformas;
    private Set<String> generos;
    private Set<String> tags;
    private Set<String> publishers;
    private Set<String> developers;
}
