package com.miproyecto.gamestack.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="videojuegos")
public class Videojuego implements Serializable {
    private static final long serialVersionUID = 1L;
    float puntuacionPromedio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String titulo;
    private String sinopsis;
    private String imagen;
    private String desarrollador;
    private String distribuidor;
    private String director;
    private String productor;
    @Column(name = "fecha_lanzamiento")
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;

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

    public Videojuego() {

    }

    public Videojuego(String titulo, String imagen, Date fechaLanzamiento) {
        this.titulo = titulo;
        this.sinopsis = "sinopsis";
        this.imagen = imagen;
        this.desarrollador = "desarrollador";
        this.distribuidor = "distribuidor";
        this.director = "director";
        this.productor = "productor";
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public Set<Plataforma> getPlataformas() {
        return plataformas;
    }

    public Set<Genero> getGeneros(){
        return generos;
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

    public Set<Reseña> getReseñas() {
        return reseñas;
    }

    public void addReseña(Reseña reseña) {
        this.getReseñas().add(reseña);
        reseña.setVideojuego(this);
    }

    public float getPuntuacionPromedio() {
        return puntuacionPromedio;
    }

    public void setPuntuacionPromedio(float puntuacionPromedio) {
        this.puntuacionPromedio = puntuacionPromedio;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void setPlataformas(Set<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

    public void setReseñas(Set<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public void setPuntuacionPromedio(int puntuacionPromedio) {
        this.puntuacionPromedio = puntuacionPromedio;
    }

    public Set<RegistroJuegoLista> getRegistroJuegoLista() {
        return registroJuegoLista;
    }

    public void addRegistroJuegoLista(RegistroJuegoLista registroJuegoLista) {
        this.getRegistroJuegoLista().add(registroJuegoLista);
        registroJuegoLista.setVideojuego(this);
    }

    public Set<Usuario> getUsuarioRecomendacion() {
        return usuarioRecomendacion;
    }

    public void addUsuarioRecomendacion(Usuario usuario) {
        this.getUsuarioRecomendacion().add(usuario);
        usuario.getRecomendaciones().add(this);
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
