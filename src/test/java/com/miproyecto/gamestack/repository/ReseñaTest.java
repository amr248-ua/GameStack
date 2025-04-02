package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Reseña;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Sql(scripts = "/cleanDb.sql")
public class ReseñaTest {
    @Autowired
    private ReseñaRepository reseñaRepository;

    @Test
    public void testCreateReseña() {

    }
}
