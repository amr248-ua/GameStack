package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.VideojuegoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/videojuegos-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class VideojuegoServiceTest {
    @Autowired
    VideojuegoService videojuegoService;

    @Test
    public void obtenerVideojuegosPaginados() {
        // GIVEN
        int page = 0;
        int size = 10;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.obtenerVideojuegosPaginados(page, size);

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
        assertTrue(videojuegos.getSize() <= size);
    }

    @Test
    public void buscarVideojuegosPorNombre() {
        // GIVEN
        String titulo = "Red Dead";
        int page = 0;
        int size = 5;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.buscarVideojuegosPorNombre(titulo, page, size);

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
        assertTrue(videojuegos.getContent().stream().allMatch(v -> v.getTitulo().toLowerCase().contains(titulo.toLowerCase())));
    }

    @Test
    public void buscarVideojuegosPorGenero() {
        // GIVEN
        String genero = "Action";
        int page = 0;
        int size = 5;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.buscarVideojuegosPorGenero(genero, page, size);

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
    }

    @Test
    public void buscarVideojuegosPorPlataforma() {
        // GIVEN
        String plataforma = "PC";
        int page = 0;
        int size = 5;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.buscarVideojuegosPorPlataforma(plataforma, page, size);

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
    }

    @Test
    public void buscarVideojuegosOrdenarPor_FechaAscendente() {
        // GIVEN
        String ordenarPor = "fechaLanzamientoAsc";
        int page = 0;
        int size = 5;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.buscarVideojuegosOrdenarPor(ordenarPor, page, size);
        videojuegos.getContent().forEach(v -> System.out.println(v.getTitulo() + " - " + v.getFechaLanzamiento()));

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
        for (int i = 1; i < videojuegos.getContent().size(); i++) {
            assertTrue(videojuegos.getContent().get(i - 1).getFechaLanzamiento()
                    .isBefore(videojuegos.getContent().get(i).getFechaLanzamiento()));
        }
    }

    @Test
    public void buscarVideojuegosOrdenarPor_FechaDescendente() {
        // GIVEN
        String ordenarPor = "fechaLanzamientoDesc";
        int page = 0;
        int size = 5;

        // WHEN
        Page<VideojuegoData> videojuegos = videojuegoService.buscarVideojuegosOrdenarPor(ordenarPor, page, size);

        // THEN
        assertNotNull(videojuegos);
        assertFalse(videojuegos.isEmpty());
        for (int i = 1; i < videojuegos.getContent().size(); i++) {
            assertTrue(videojuegos.getContent().get(i - 1).getFechaLanzamiento()
                    .isAfter(videojuegos.getContent().get(i).getFechaLanzamiento()));
        }
    }
}
