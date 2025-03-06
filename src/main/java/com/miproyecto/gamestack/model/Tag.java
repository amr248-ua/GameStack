package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="tags")
public class Tag implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tag;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_videojuego",
            joinColumns = { @JoinColumn(name = "fk_tag") },
            inverseJoinColumns = {@JoinColumn(name = "fk_videojuego")})
    Set<Videojuego> videojuegos = new HashSet<>();

    public Tag() {}
    public Tag(String tag) {
        this.tag = tag;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public Set<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public void addVideojuegos(Videojuego videojuego) {
        this.getVideojuegos().add(videojuego);
        videojuego.getTags().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        if (id != null && tag.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, tag.id);
        return Objects.equals(this.tag, tag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
