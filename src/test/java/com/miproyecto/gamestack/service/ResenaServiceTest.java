package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.ReseñaData;
import com.miproyecto.gamestack.model.Reseña;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.ReseñaRepository;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResenaServiceTest {
    @Autowired
    ReseñaService reseñaService;
    @Autowired
    ReseñaRepository reseñaRepository;
    @Autowired
    VideojuegoRepository videojuegoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public void setUp() {
        Videojuego videojuego = new Videojuego();
        videojuego.setTitulo("Videojuego de prueba");

        Usuario usuario = new Usuario();
        usuario.setUsername("testUser");
        usuario.setEmail("test@test.com");

        // Guardar el videojuego y el usuario en la base de datos
        videojuegoRepository.save(videojuego);
        usuarioRepository.save(usuario);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba
        reseñaRepository.deleteAll();
        videojuegoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    public void testObtenerReseñasPorVideojuegoId() {
        //GIVEN
        setUp();
        String titulo = "Videojuego de prueba";
        String username = "testUser";
        int page = 0;
        int size = 10;

        Reseña reseña = new Reseña();
        reseña.setContenido("Esta es una reseña de prueba");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion(new Date());
        reseña.setUsuario(usuarioRepository.findByUsername(username).orElse(null));
        Videojuego videojuego = videojuegoRepository.findByTitulo(titulo).orElse(null);
        reseña.setVideojuego(videojuego);

        reseñaRepository.save(reseña);

        //WHEN
        var reseñas = reseñaService.obtenerReseñasPorVideojuegoId(videojuego.getId(), page, size);

        //THEN
        assertNotNull(reseñas);
        assertFalse(reseñas.isEmpty());

    }

    @Test
    public void testCrearReseña() {
        //GIVEN
        ReseñaData reseña = new ReseñaData();
        reseña.setContenido("Esta es una reseña");
        reseña.setRecomienda(true);

        //WHEN then
        assertDoesNotThrow(() -> {
            reseñaService.crearReseña(reseña, "1", "test2");
        });
    }

    @Test
    public void testCrearReseñaExistente() {
        //GIVEN
        setUp();
        ReseñaData reseña = new ReseñaData();
        reseña.setContenido("Esta es una reseña de prueba");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion("2025-01-01");
        Videojuego videojuego = videojuegoRepository.findByTitulo("Videojuego de prueba").orElse(null);

        Reseña reseñaExistente = new Reseña();
        reseñaExistente.setContenido("Esta es una reseña de prueba");
        reseñaExistente.setRecomienda(true);
        reseñaExistente.setActivo(true);
        reseñaExistente.setFechaCreacion(new Date());
        reseñaExistente.setUsuario(usuarioRepository.findByUsername("testUser").orElse(null));
        reseñaExistente.setVideojuego(videojuego);
        reseñaRepository.save(reseñaExistente);

        //WHEN
        assertThrows(ReseñaServiceException.class, () -> {
            reseñaService.crearReseña(reseña, videojuego.getId().toString(), "testUser");
        });
    }

    @Test
    public void testPorcentajeReseñasPositivas() {
        //GIVEN
        setUp();
        Long videojuegoId = 1L;
        int page = 0;
        int size = 10;
        Reseña reseña = new Reseña();
        reseña.setContenido("Esta es una reseña de prueba");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion(new Date());
        reseña.setUsuario(usuarioRepository.findById(1L).orElse(null));
        reseña.setVideojuego(videojuegoRepository.findById(videojuegoId).orElse(null));
        reseñaRepository.save(reseña);

        //WHEN
        var reseñas = reseñaService.obtenerReseñasPorVideojuegoId(videojuegoId, page, size);
        int porcentaje = reseñaService.porcentajeReseñasPositivas(reseñas);

        //THEN
        assertThat(porcentaje).isEqualTo(100);
    }

}
