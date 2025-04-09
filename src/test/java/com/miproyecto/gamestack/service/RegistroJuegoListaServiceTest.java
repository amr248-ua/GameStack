package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RegistroJuegoListaData;
import com.miproyecto.gamestack.model.RegistroJuegoLista;
import com.miproyecto.gamestack.model.TipoLista;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.RegistroJuegoListaRepository;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RegistroJuegoListaServiceTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private RegistroJuegoListaService registroJuegoListaService;
    @Autowired
    private RegistroJuegoListaRepository registroJuegoListaRepository;

    @Test
    public void testServiceCrearRegistroJuegoLista() {
        //GIVEN
        Videojuego videojuego = new Videojuego();
        videojuego.setTitulo("Videojuego de prueba");

        Usuario usuario = new Usuario();
        usuario.setUsername("testUser");
        usuario.setEmail("test@test.com");

        videojuegoRepository.save(videojuego);
        usuarioRepository.save(usuario);
        RegistroJuegoListaData registroJuegoListaData = new RegistroJuegoListaData();

        registroJuegoListaData.setTipoLista(TipoLista.COMPLETADO);
        registroJuegoListaData.setPuntuacion(5);
        registroJuegoListaData.setFechaInicio(LocalDate.of(2023, 1, 1));
        registroJuegoListaData.setFechaFin(LocalDate.of(2023, 1, 2));

        //WHEN
        registroJuegoListaService.crearRegistroJuegoLista(registroJuegoListaData, String.valueOf(videojuego.getId()), usuario.getUsername());

        //THEN
        List<RegistroJuegoLista> registroJuegoLista = registroJuegoListaRepository.findByUsuarioId(usuario.getId()).orElse(null);
        assertThat(registroJuegoLista).isNotNull();
        assertThat(registroJuegoLista.size()).isEqualTo(1);
        assertThat(registroJuegoLista.get(0).getTipoLista()).isEqualTo(TipoLista.COMPLETADO);
        assertThrows(RegistroJuegoListaException.class, () -> {
            registroJuegoListaService.crearRegistroJuegoLista(registroJuegoListaData, String.valueOf(videojuego.getId()), usuario.getUsername());
        });
    }

}
