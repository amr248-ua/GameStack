package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "reseñas")
@Getter
@Setter
public class Reseña implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String contenido;
    private Boolean recomienda;
    private Boolean activo = true;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_videojuego")
    private Videojuego videojuego;

    @OneToMany
    Set<Reporte> reportes = new HashSet<>();

    public Reseña() {
    }

    public Reseña(@NotNull String contenido) {
        this.contenido = contenido;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reseña reseña = (Reseña) o;
        if (id != null && reseña.id != null)
            return Objects.equals(id, reseña.id);
        return this.contenido.equals(reseña.contenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenido);
    }
}
