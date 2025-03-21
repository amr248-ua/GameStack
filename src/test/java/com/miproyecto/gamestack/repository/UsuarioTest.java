package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/cleanDb.sql")
public class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    @Transactional
    public void testCreateUsuario() throws ParseException {
        //GIVEN
        Usuario usuario = new Usuario();

        //WHEN
        usuario.setUsername("test");
        usuario.setPassword("test");
        usuario.setEmail("test@test.com");
        usuario.setBiografia("test");
        LocalDate fechaNacimiento = LocalDate.now();
        usuario.setFechaNacimiento(fechaNacimiento);

        //THEN
        assertThat(usuario.getUsername()).isEqualTo("test");
        assertThat(usuario.getPassword()).isEqualTo("test");
        assertThat(usuario.getEmail()).isEqualTo("test@test.com");
        assertThat(usuario.getBiografia()).isEqualTo("test");
        assertThat(usuario.getFechaNacimiento()).isEqualTo(fechaNacimiento);
    }
}
