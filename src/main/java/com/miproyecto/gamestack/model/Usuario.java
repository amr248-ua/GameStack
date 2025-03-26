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
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String steamId;
    private String codigoActivacion;
    private String fotoPerfil;
    private String biografia;
    private String privacidadLista;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private boolean admin = false;
    private boolean bloqueado = false;
    private boolean activo = false;
    private boolean moderador = false;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "usuario")
    Set<Foro> foros = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    Set<Notificacion> notificaciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    Set<Reseña> reseñas = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    Set<Reporte> reportes = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    Set<RegistroJuegoLista> registroJuegoLista = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_videojuego",
            joinColumns = { @JoinColumn(name = "fk_usuario") },
            inverseJoinColumns = {@JoinColumn(name = "fk_videojuego")})
    Set<Videojuego> recomendaciones = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    Set<Chatbot> chatbots = new HashSet<>();

    // Constructor vacío necesario para JPA/Hibernate.
    // No debe usarse desde la aplicación.
    public Usuario() {
    }

    // Constructor público con los atributos obligatorios. En este caso el correo electrónico.
    public Usuario(String email,String username) {
        this.email = email;
        this.username = username;
    }

    public void addForo(Foro foro) {
        this.getForos().add(foro);
        foro.setUsuario(this);
    }

    public void addComentario(Comentario comentario) {
        this.getComentarios().add(comentario);
        comentario.setUsuario(this);
    }

    public void addNotificacion(Notificacion notificacion) {
        this.getNotificaciones().add(notificacion);
        notificacion.setUsuario(this);
    }

    public void addReseña(Reseña reseña) {
        this.getReseñas().add(reseña);
        reseña.setUsuario(this);
    }

    public void addRegistroJuegoLista(RegistroJuegoLista registroJuegoLista) {
        this.getRegistroJuegoLista().add(registroJuegoLista);
        registroJuegoLista.setUsuario(this);
    }

    public void addReporte(Reporte reporte) {
        this.getReportes().add(reporte);
        reporte.setUsuario(this);
    }

    public void addRecomendacion(Videojuego videojuego) {
        this.getRecomendaciones().add(videojuego);
        videojuego.getUsuarioRecomendacion().add(this);
    }

    public void addChatbot(Chatbot chatbot) {
        this.getChatbots().add(chatbot);
        chatbot.setUsuario(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        if (id != null && usuario.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, usuario.id);
        // si no comparamos por campos obligatorios
        return this.email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(username);
    }
}
