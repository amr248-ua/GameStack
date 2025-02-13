package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "registro_juego_lista")

public class RegistroJuegoLista implements Serializable{

    private static final long serialVersionUID = 1L;

    private Float horas;
    private Integer appId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tipoLista;
    private Integer puntuacion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_videojuego")
    private Videojuego videojuego;

    @OneToOne(mappedBy = "registroJuegoLista")
    private EstadisticasSteam estadisticasSteam;


    public RegistroJuegoLista() {
    }

    public RegistroJuegoLista(@NotNull String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHoras() {
        return horas;
    }

    public void setHoras(Float horas) {
        this.horas = horas;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public EstadisticasSteam getEstadisticasSteam() {
        return estadisticasSteam;
    }

    public void setEstadisticasSteam(EstadisticasSteam estadisticasSteam) {
        this.estadisticasSteam = estadisticasSteam;
    }

    public void addEstadisticasSteam(EstadisticasSteam estadisticasSteam) {
        this.estadisticasSteam = estadisticasSteam;
        estadisticasSteam.setRegistroJuegoLista(this);
    }

    public void removeEstadisticasSteam(EstadisticasSteam estadisticasSteam) {
        this.estadisticasSteam = null;
        estadisticasSteam.setRegistroJuegoLista(null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroJuegoLista registro = (RegistroJuegoLista) o;
        if (id != null && registro.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, registro.id);
        // si no comparamos por campos obligatorios
        return this.tipoLista.equals(registro.tipoLista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
