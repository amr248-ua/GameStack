package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.RegistroJuegoLista;
import com.miproyecto.gamestack.model.TipoLista;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.model.Videojuego;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RegistroJuegoListaTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private RegistroJuegoListaRepository registroJuegoListaRepository;

    @Test
    public void testCrearRegistroJuegoLista() {
        //GIVEN
        RegistroJuegoLista registroJuegoLista = new RegistroJuegoLista();

        //WHEN
        registroJuegoLista.setUsuario(new Usuario("test", "test"));
        registroJuegoLista.setVideojuego(new Videojuego("test", "test", "test", LocalDate.of(2023, 1, 1)));
        registroJuegoLista.setTipoLista(TipoLista.COMPLETADO);
        registroJuegoLista.setPuntuacion(5);
        registroJuegoLista.setFechaInicio(LocalDate.of(2023, 1, 1));
        registroJuegoLista.setFechaFin(LocalDate.of(2023, 1, 2));

        //THEN
        assertThat(registroJuegoLista.getUsuario().getUsername()).isEqualTo("test");
        assertThat(registroJuegoLista.getVideojuego().getTitulo()).isEqualTo("test");
        assertThat(registroJuegoLista.getTipoLista()).isNotNull();
    }

    @Test
    public void testIgualdadRegistroJuegoListaConId() {
        //GIVEN
        RegistroJuegoLista registroJuegoLista1 = new RegistroJuegoLista();
        RegistroJuegoLista registroJuegoLista2 = new RegistroJuegoLista();

        //WHEN
        registroJuegoLista1.setId(1L);
        registroJuegoLista2.setId(1L);

        //THEN
        assertThat(registroJuegoLista1).isEqualTo(registroJuegoLista2);
    }

    @Test
    public void testIgualdadRegistroJuegoListaIdDiferente() {
        //GIVEN
        RegistroJuegoLista registroJuegoLista1 = new RegistroJuegoLista();
        RegistroJuegoLista registroJuegoLista2 = new RegistroJuegoLista();

        //WHEN
        registroJuegoLista1.setId(1L);
        registroJuegoLista2.setId(2L);

        //THEN
        assertThat(registroJuegoLista1).isNotEqualTo(registroJuegoLista2);
    }

    @Test
    public void testGuardarRegistroJuegoLista() {
        //GIVEN
        Videojuego videojuego = new Videojuego();
        videojuego.setTitulo("Videojuego de prueba");

        Usuario usuario = new Usuario();
        usuario.setUsername("testUser");
        usuario.setEmail("test@test.com");

        videojuegoRepository.save(videojuego);
        usuarioRepository.save(usuario);

        RegistroJuegoLista registroJuegoLista = new RegistroJuegoLista();
        registroJuegoLista.setUsuario(usuario);
        registroJuegoLista.setVideojuego(videojuego);
        registroJuegoLista.setTipoLista(TipoLista.COMPLETADO);
        registroJuegoLista.setPuntuacion(5);
        registroJuegoLista.setFechaInicio(LocalDate.of(2023, 1, 1));
        registroJuegoLista.setFechaFin(LocalDate.of(2023, 1, 2));

        //WHEN
        RegistroJuegoLista savedRegistro = registroJuegoListaRepository.save(registroJuegoLista);

        //THEN
        assertThat(savedRegistro.getId()).isNotNull();
    }
}
