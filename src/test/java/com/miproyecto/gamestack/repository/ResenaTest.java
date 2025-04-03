package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Reseña;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Sql(scripts = "/reseñas-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ResenaTest {
    @Autowired
    private ReseñaRepository reseñaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Test
    public void testCreateReseña() {
        //GIVEN
        Reseña reseña = new Reseña();

        //WHEN
        reseña.setContenido("test");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion(new java.util.Date());
        reseña.setUsuario(usuarioRepository.findByUsername("test").orElse(null));
        reseña.setVideojuego(videojuegoRepository.findById(1L).orElse(null));

        //THEN
        assertThat(reseña.getContenido()).isEqualTo("test");
        assertThat(reseña.getRecomienda()).isEqualTo(true);
        assertThat(reseña.getActivo()).isEqualTo(true);
        assertThat(reseña.getFechaCreacion()).isNotNull();
        assertThat(reseña.getUsuario().getUsername()).isEqualTo("test");
        assertThat(reseña.getVideojuego().getId()).isEqualTo(1L);
    }

    @Test
    public void testIgualdadReseñasConId() {
        //GIVEN
        Reseña reseña1 = new Reseña();
        Reseña reseña2 = new Reseña();

        //WHEN
        reseña1.setId(1L);
        reseña2.setId(1L);

        //THEN
        assertThat(reseña1).isEqualTo(reseña2);
    }

    @Test
    public void testIgualdadReseñasSinId() {
        //GIVEN
        Reseña reseña1 = new Reseña();
        Reseña reseña2 = new Reseña();

        //WHEN
        reseña1.setContenido("test");
        reseña2.setContenido("test");

        //THEN
        assertThat(reseña1).isEqualTo(reseña2);
    }

    @Test
    public void testDiferenciaReseñasConId() {
        //GIVEN
        Reseña reseña1 = new Reseña();
        Reseña reseña2 = new Reseña();

        //WHEN
        reseña1.setId(1L);
        reseña2.setId(2L);

        //THEN
        assertThat(reseña1).isNotEqualTo(reseña2);
    }

    @Test
    public void testDiferenciaReseñasSinId() {
        //GIVEN
        Reseña reseña1 = new Reseña();
        Reseña reseña2 = new Reseña();

        //WHEN
        reseña1.setContenido("test1");
        reseña2.setContenido("test2");

        //THEN
        assertThat(reseña1).isNotEqualTo(reseña2);
    }

    @Test
    public void testGuardarReseña() {
        //GIVEN
        Reseña reseña = new Reseña();
        reseña.setContenido("test");
        reseña.setRecomienda(true);
        reseña.setActivo(true);
        reseña.setFechaCreacion(new java.util.Date());
        reseña.setUsuario(usuarioRepository.findByUsername("test").orElse(null));
        reseña.setVideojuego(videojuegoRepository.findById(1L).orElse(null));

        //WHEN
        reseñaRepository.save(reseña);

        //THEN
        assertThat(reseña.getId()).isNotNull();
    }
}
