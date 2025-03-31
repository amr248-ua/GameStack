package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Videojuego;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/cleanDb.sql")
public class VideojuegoTest {
    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Test
    public void testCreateVideojuego() throws ParseException {
        //GIVEN
        Videojuego videojuego = new Videojuego();

        //WHEN
        videojuego.setTitulo("test");
        videojuego.setSinopsis("test");
        videojuego.setImagen("test");

        //THEN
        assertThat(videojuego.getTitulo()).isEqualTo("test");
        assertThat(videojuego.getSinopsis()).isEqualTo("test");
        assertThat(videojuego.getImagen()).isEqualTo("test");
    }

    @Test
    public void testIgualdadVideojuegosConId() {
        //GIVEN
        Videojuego videojuego1 = new Videojuego();
        Videojuego videojuego2 = new Videojuego();

        //WHEN
        videojuego1.setId(1L);
        videojuego2.setId(1L);

        //THEN
        assertThat(videojuego1).isEqualTo(videojuego2);
    }

    @Test
    public void testIgualdadVideojuegosSinId() {
        //GIVEN
        Videojuego videojuego1 = new Videojuego();
        Videojuego videojuego2 = new Videojuego();

        //WHEN
        videojuego1.setTitulo("test");
        videojuego2.setTitulo("test");

        //THEN
        assertThat(videojuego1).isEqualTo(videojuego2);
    }

    @Test
    public void testDiferenciaVideojuegosConId() {
        //GIVEN
        Videojuego videojuego1 = new Videojuego();
        Videojuego videojuego2 = new Videojuego();

        //WHEN
        videojuego1.setId(1L);
        videojuego2.setId(2L);

        //THEN
        assertThat(videojuego1).isNotEqualTo(videojuego2);
    }

    @Test
    public void testDiferenciaVideojuegosSinId() {
        //GIVEN
        Videojuego videojuego1 = new Videojuego();
        Videojuego videojuego2 = new Videojuego();

        //WHEN
        videojuego1.setTitulo("test1");
        videojuego2.setTitulo("test2");

        //THEN
        assertThat(videojuego1).isNotEqualTo(videojuego2);
    }

    @Test
    public void guardarVideojuego() {
        //GIVEN
        Videojuego videojuego = new Videojuego();
        videojuego.setTitulo("test");
        videojuego.setSinopsis("test");
        videojuego.setFechaLanzamiento(LocalDate.now());
        videojuego.setImagen("test");

        //WHEN
        videojuegoRepository.save(videojuego);

        //THEN
        Videojuego videojuegoBD = videojuegoRepository.findById(1L).orElse(null);
        assertThat(videojuegoBD).isNotNull();
        assertThat(videojuegoBD.getTitulo()).isEqualTo("test");
        assertThat(videojuegoBD.getSinopsis()).isEqualTo("test");
        assertThat(videojuegoBD.getImagen()).isEqualTo("test");
    }

}
