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

    private Set<PlataformaData> plataformas;
    private Set<GeneroData> generos;
    private Set<TagData> tags;
    private Set<String> publishers;
    private Set<String> developers;
}
