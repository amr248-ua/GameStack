package com.miproyecto.gamestack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String steamId;
    private String codigoActivavion;
    private String fotoPerfil;
    private String biografia;
    private String privacidadLista;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    private String nombre;
    private String password;
    private boolean admin = false;
    private boolean bloqueado = false;
    private boolean activo = false;
    private boolean moderador = false;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

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
    public Usuario(String email) {
        this.email = email;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getCodigoActivavion() {
        return codigoActivavion;
    }

    public void setCodigoActivavion(String codigoActivavion) {
        this.codigoActivavion = codigoActivavion;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getPrivacidadLista() {
        return privacidadLista;
    }

    public void setPrivacidadLista(String privacidadLista) {
        this.privacidadLista = privacidadLista;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    // Getters y setters atributos básicos
    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Long getId() {
        return id;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getModerador() {
        return moderador;
    }

    public void setModerador(boolean moderador) {
        this.moderador = moderador;
    }

    public Set<Foro> getForos() {
        return foros;
    }

    public void addForo(Foro foro) {
        this.getForos().add(foro);
        foro.setUsuario(this);
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void addComentario(Comentario comentario) {
        this.getComentarios().add(comentario);
        comentario.setUsuario(this);
    }

    public Set<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void addNotificacion(Notificacion notificacion) {
        this.getNotificaciones().add(notificacion);
        notificacion.setUsuario(this);
    }

    public Set<Reseña> getReseñas() {
        return reseñas;
    }

    public void addReseña(Reseña reseña) {
        this.getReseñas().add(reseña);
        reseña.setUsuario(this);
    }

    public Set<RegistroJuegoLista> getRegistroJuegoLista() {
        return registroJuegoLista;
    }

    public void addRegistroJuegoLista(RegistroJuegoLista registroJuegoLista) {
        this.getRegistroJuegoLista().add(registroJuegoLista);
        registroJuegoLista.setUsuario(this);
    }

    public Set<Reporte> getReportes() {
        return reportes;
    }

    public void addReporte(Reporte reporte) {
        this.getReportes().add(reporte);
        reporte.setUsuario(this);
    }

    public Set<Videojuego> getRecomendaciones() {
        return recomendaciones;
    }

    public void addRecomendacion(Videojuego videojuego) {
        this.getRecomendaciones().add(videojuego);
        videojuego.getUsuarioRecomendacion().add(this);
    }

    public void setRecomendaciones(Set<Videojuego> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Set<Chatbot> getChatbots() {
        return chatbots;
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
        return this.nombre.equals(usuario.nombre);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(nombre);
    }
}
