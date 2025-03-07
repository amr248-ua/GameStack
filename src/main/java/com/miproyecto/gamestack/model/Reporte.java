package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reportes")
@Getter
@Setter
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String motivo;
    private String descripcion;
    private Boolean resuelto = false;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_comentario")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "fk_reseña")
    private Reseña reseña;

    @ManyToOne
    @JoinColumn(name = "fk_foro")
    private Foro foro;

    public Reporte() {
    }

    public Reporte(@NotNull String motivo) {
        this.motivo = motivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reporte reporte = (Reporte) o;
        if (id != null && reporte.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, reporte.id);
        // si no comparamos por campos obligatorios
        return descripcion.equals(reporte.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion);
    }
}
