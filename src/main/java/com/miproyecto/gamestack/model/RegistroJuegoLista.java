package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "registro_juego_lista")
@Getter
@Setter
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
