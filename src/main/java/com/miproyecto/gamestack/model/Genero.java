package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="plataformas")
public class Genero implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String genero;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genero_videojuego",
            joinColumns = { @JoinColumn(name = "fk_genero") },
            inverseJoinColumns = {@JoinColumn(name = "fk_videojuego")})
    Set<Videojuego> videojuegos = new HashSet<>();

    public Genero() {}
    public Genero(String genero) {
        this.genero = genero;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public Set<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public void addVideojuegos(Videojuego videojuego) {
        this.getVideojuegos().add(videojuego);
        videojuego.getGeneros().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        if (id != null && genero.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, genero.id);
        // si no comparamos por campos obligatorios
        return this.genero.equals(genero.genero);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(genero);
    }
}
