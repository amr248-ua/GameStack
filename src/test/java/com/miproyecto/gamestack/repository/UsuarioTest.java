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

    @Test
    public void testIgualdadUsuariosConId() {
        //GIVEN
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        //WHEN
        usuario1.setId(1L);
        usuario2.setId(1L);

        //THEN
        assertThat(usuario1).isEqualTo(usuario2);
    }

    @Test
    public void testIgualdadUsuariosSinId() {
        //GIVEN
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        //WHEN
        usuario1.setEmail("test@test.com");
        usuario2.setEmail("test@test.com");

        //THEN
        assertThat(usuario1).isEqualTo(usuario2);
    }

    @Test
    public void testNotIgualdadUsuarios() {
        //GIVEN
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        //WHEN
        usuario1.setEmail("test1@test.com");
        usuario2.setEmail("test2@test.com");

        //THEN
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    @Transactional
    public void testGuardarUsuario() {
        //GIVEN
        Usuario usuario = new Usuario("test@test.com");
        usuario.setUsername("test");
        usuario.setPassword("123456");

        //WHEN
        usuarioRepository.save(usuario);

        //THEN
        assertThat(usuario.getId()).isNotNull();

        Usuario usuarioGuardado = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getUsername()).isEqualTo("test");
        assertThat(usuarioGuardado.getPassword()).isEqualTo("123456");
        assertThat(usuarioGuardado.getEmail()).isEqualTo("test@test.com");

    }

    @Test
    @Transactional
    public void buscarUsuarioPorEmail() {
        //GIVEN
        Usuario usuario = new Usuario("test@test.com");
        usuarioRepository.save(usuario);

        //WHEN
        Usuario usuarioGuardado = usuarioRepository.findByEmail("test@test.com").orElse(null);

        //THEN
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getEmail()).isEqualTo("test@test.com");
    }

}
