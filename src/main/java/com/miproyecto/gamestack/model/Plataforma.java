package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="plataformas")
@Getter
@Setter
public class Plataforma implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String plataforma;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "plataforma_videojuego",
            joinColumns = { @JoinColumn(name = "fk_plataforma") },
            inverseJoinColumns = {@JoinColumn(name = "fk_videojuego")})
    Set<Videojuego> videojuegos = new HashSet<>();

    public Plataforma() {}

    public Plataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public void addVideojuego(Videojuego videojuego) {
        // Hay que actualiar ambas colecciones, porque
        // JPA/Hibernate no lo hace automáticamente
        this.getVideojuegos().add(videojuego);
        videojuego.getPlataformas().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plataforma plataforma = (Plataforma) o;
        if (id != null && plataforma.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, plataforma.id);
        // si no comparamos por campos obligatorios
        return this.plataforma.equals(plataforma.plataforma);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(plataforma);
    }
}
