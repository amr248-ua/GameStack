package com.miproyecto.gamestack.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import java.util.Objects;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String asunto;
    private String mensaje;
    private Boolean leida = false;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    public Notificacion() {
    }

    public Notificacion(@NotNull String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacion notificacion = (Notificacion) o;
        if (id != null && notificacion.id != null)
            return Objects.equals(id, notificacion.id);
        return this.mensaje.equals(notificacion.mensaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mensaje);
    }
}
