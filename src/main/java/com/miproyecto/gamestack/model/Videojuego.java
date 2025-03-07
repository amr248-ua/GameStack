package com.miproyecto.gamestack.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="videojuegos")
@Getter
@Setter
public class Videojuego implements Serializable {
    private static final long serialVersionUID = 1L;
    float puntuacionPromedio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;
    @Column(length = 2500)
    private String sinopsis;
    private String imagen;
    @Column(name = "fecha_lanzamiento")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaLanzamiento;

    @ManyToMany(mappedBy="videojuegos")
    Set<Plataforma> plataformas = new HashSet<>();

    @ManyToMany(mappedBy = "videojuegos")
    Set<Genero> generos = new HashSet<>();

    @OneToMany(mappedBy = "videojuego")
    Set<Reseña> reseñas = new HashSet<>();

    @OneToMany(mappedBy = "videojuego")
    Set<RegistroJuegoLista> registroJuegoLista = new HashSet<>();

    @ManyToMany(mappedBy = "recomendaciones")
    Set<Usuario> usuarioRecomendacion = new HashSet<>();

    @ManyToMany(mappedBy = "videojuegos")
    Set<Tag> tags = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "videojuego_publishers", joinColumns = @JoinColumn(name = "videojuego_id"))
    @Column(name = "publisher")
    private Set<String> publishers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "videojuego_developers", joinColumns = @JoinColumn(name = "videojuego_id"))
    @Column(name = "developer")
    private Set<String> developers = new HashSet<>();

    public Videojuego() {

    }

    public Videojuego(String titulo,String sinopsis, String imagen, LocalDate fechaLanzamiento) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void addPlataforma(Plataforma plataforma) {
        // Hay que actualiar ambas colecciones, porque
        // JPA/Hibernate no lo hace automáticamente
        this.getPlataformas().add(plataforma);
        plataforma.getVideojuegos().add(this);
    }

    public void addGenero(Genero genero){
        this.getGeneros().add(genero);
        genero.getVideojuegos().add(this);
    }

    public void addReseña(Reseña reseña) {
        this.getReseñas().add(reseña);
        reseña.setVideojuego(this);
    }

    public void addRegistroJuegoLista(RegistroJuegoLista registroJuegoLista) {
        this.getRegistroJuegoLista().add(registroJuegoLista);
        registroJuegoLista.setVideojuego(this);
    }

    public void addUsuarioRecomendacion(Usuario usuario) {
        this.getUsuarioRecomendacion().add(usuario);
        usuario.getRecomendaciones().add(this);
    }

    public void addTag(Tag tag) {
        this.getTags().add(tag);
        tag.getVideojuegos().add(this);
    }

    public void addPublisher(String publisher) {
        this.getPublishers().add(publisher);
    }

    public void addDeveloper(String developer) {
        this.getDevelopers().add(developer);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Videojuego videojuego = (Videojuego) o;
        if (id != null && videojuego.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, videojuego.id);
        // si no comparamos por campos obligatorios
        return titulo.equals(videojuego.titulo);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(titulo);
    }


}
