package com.miproyecto.gamestack.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "chatbot_mensajes")
public class ChatbotMensaje {

    private static final long serialVersionUID = 1L;

    private Boolean valoracion;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String mensaje;
    private String respuesta;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "fk_chatbot")
    private Chatbot chatbot;

    public ChatbotMensaje() {
    }

    public ChatbotMensaje(@NotNull String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Chatbot getChatbot() {
        return chatbot;
    }

    public void setChatbot(Chatbot chatbot) {
        this.chatbot = chatbot;
    }

    public Boolean getValoracion() {
        return valoracion;
    }

    public void setValoracion(Boolean valoracion) {
        this.valoracion = valoracion;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatbotMensaje chatbotMensaje = (ChatbotMensaje) o;
        if (id != null && chatbotMensaje.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, chatbotMensaje.id);
        // si no comparamos por campos obligatorios
        return this.mensaje.equals(chatbotMensaje.mensaje);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(mensaje);
    }
}
