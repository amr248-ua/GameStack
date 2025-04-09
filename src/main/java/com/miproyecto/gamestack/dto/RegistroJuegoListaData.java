package com.miproyecto.gamestack.dto;

import com.miproyecto.gamestack.model.TipoLista;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class RegistroJuegoListaData {
    private UsuarioData usuario;
    private VideojuegoData videojuego;
    private TipoLista tipoLista;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Float horas = 0f;
    private int puntuacion;
}
