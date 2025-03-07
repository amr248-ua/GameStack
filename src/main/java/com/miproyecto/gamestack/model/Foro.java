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
@Table(name = "foros")
@Getter
@Setter
public class Foro implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tema;
    private String contenido;
    private Boolean activo = true;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "foro")
    Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(mappedBy = "foro")
    Set<Reporte> reportes = new HashSet<>();

    public Foro() {}
    public Foro(@NotNull String tema) {
        this.tema = tema;
    }


    public void addComentario(Comentario comentario) {
        this.getComentarios().add(comentario);
        comentario.setForo(this);
    }

    public void addReporte(Reporte reporte) {
        this.getReportes().add(reporte);
        reporte.setForo(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foro foro = (Foro) o;
        if (id != null && foro.id != null)
            return Objects.equals(id, foro.id);
        return this.tema.equals(foro.tema);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tema);
    }

}
