package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.ReseñaData;
import com.miproyecto.gamestack.model.Reseña;
import com.miproyecto.gamestack.repository.ReseñaRepository;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/reseñas-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ResenaServiceTest {
    @Autowired
    ReseñaService reseñaService;
    @Autowired
    ReseñaRepository reseñaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    VideojuegoRepository videojuegoRepository;

    @Test
    public void testObtenerReseñasPorVideojuegoId() {
        //GIVEN
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

        //WHEN
        assertDoesNotThrow(() -> {
            reseñaService.crearReseña(reseña, "1", "test2");
        });

        //THEN
        assertNotNull(reseñaRepository.findById(1L));
    }

    @Test
    public void testCrearReseñaExistente() {
        //GIVEN
        ReseñaData reseña = new ReseñaData();
        reseña.setContenido("Esta es una reseña de prueba");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion("2025-01-01");

        //WHEN
        assertThrows(ReseñaServiceException.class, () -> {
            reseñaService.crearReseña(reseña, "1", "test2");
        });
    }

    @Test
    public void testPorcentajeReseñasPositivas() {
        //GIVEN
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
