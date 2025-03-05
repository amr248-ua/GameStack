package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="plataformas")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlataforma() {
        return plataforma;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public Set<Videojuego> getVideojuegos() {
        return videojuegos;
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
