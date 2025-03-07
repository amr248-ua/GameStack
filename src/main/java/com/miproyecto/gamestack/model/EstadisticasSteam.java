package com.miproyecto.gamestack.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.Objects;


@Entity
@Table(name = "estadisticas_steam")
@Getter
@Setter
public class EstadisticasSteam implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String descripcionLogro;
    private Float horasJugadas;
    private Integer appId;

    @OneToOne
    @JoinColumn(name = "fk_registro_juego_lista")
    private RegistroJuegoLista registroJuegoLista;

    public EstadisticasSteam() {
    }

    public EstadisticasSteam(@NotNull String descripcionLogro) {
        this.descripcionLogro = descripcionLogro;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticasSteam estadisticasSteam = (EstadisticasSteam) o;
        if (id != null && estadisticasSteam.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, estadisticasSteam.id);
        // si no comparamos por campos obligatorios
        return this.appId.equals(estadisticasSteam.appId);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(appId);
    }
}
