package com.miproyecto.gamestack.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "estadisticas_steam")

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionLogro() {
        return descripcionLogro;
    }

    public void setDescripcionLogro(String descripcionLogro) {
        this.descripcionLogro = descripcionLogro;
    }

    public Float getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(Float horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public RegistroJuegoLista getRegistroJuegoLista() {
        return registroJuegoLista;
    }

    public void setRegistroJuegoLista(RegistroJuegoLista registroJuegoLista) {
        this.registroJuegoLista = registroJuegoLista;
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
