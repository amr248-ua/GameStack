package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comentarios")
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String contenido;
    private Integer likes;
    private Boolean activo = true;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @OneToMany(mappedBy = "comentario")
    Set<Reporte> reportes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_foro")
    private Foro foro;

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id") // Nombre de la columna en la base de datos
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> respuestas = new HashSet<>();


    public Comentario() {
    }

    public Comentario(@NotNull String contenido) {
        this.contenido = contenido;
    }

    public Long getId() {
        return id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Foro getForo() {
        return foro;
    }

    public void setForo(Foro foro) {
        this.foro = foro;
    }

    public Set<Reporte> getReportes() {
        return reportes;
    }

    public void addReporte(Reporte reporte) {
        this.getReportes().add(reporte);
        reporte.setComentario(this);
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    public Set<Comentario> getRespuestas() {
        return respuestas;
    }

    public void addRespuesta(Comentario respuesta) {
        this.getRespuestas().add(respuesta);
        respuesta.setComentarioPadre(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        if (id != null && that.id != null)
            return Objects.equals(id, that.id);
        return this.contenido.equals(that.contenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenido);
    }
}
