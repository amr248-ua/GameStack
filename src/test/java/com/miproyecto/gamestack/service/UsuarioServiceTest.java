package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.UsuarioData;

import com.miproyecto.gamestack.dto.VerificarCuentaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/cleanDb.sql")
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;

    private String addUsuarioBD() {
        RegistroData usuario = new RegistroData();
        usuario.setEmail("miproyecto@gmail.com");
        usuario.setUsername("miproyecto");
        usuario.setPassword("miproyecto");
        UsuarioData usuarioGuardado = usuarioService.registrar(usuario);
        return usuarioGuardado.getEmail();
    }

    @Test
    public void registrarUsuario() {
        //GIVEN
        RegistroData usuario = new RegistroData();
        usuario.setEmail("miproyecto@gmail.com");
        usuario.setUsername("miproyecto");
        usuario.setPassword("miproyecto");
        LocalDate fecha = LocalDate.of(2002,7,19);
        usuario.setFechaNacimiento(fecha);

        //WHEN
        usuarioService.registrar(usuario);

        //THEN
        UsuarioData usuarioBD = usuarioService.findByEmail("miproyecto@gmail.com");
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getEmail()).isEqualTo("miproyecto@gmail.com");

    }

    @Test
    public void usuarioYaRegistrado() {
        //GIVEN
        addUsuarioBD();
        RegistroData usuario = new RegistroData();
        usuario.setEmail("miproyecto@gmail.com");
        usuario.setUsername("miproyecto");
        usuario.setPassword("miproyecto");
        LocalDate fecha = LocalDate.of(2002, 7, 19);
        usuario.setFechaNacimiento(fecha);

        //WHEN & THEN
        assertThrows(UsuarioServiceException.class, () -> usuarioService.registrar(usuario), "El usuario con email " + usuario.getEmail() + " ya está registrado");

    }

    @Test
    public void servicioVerificarCuentaCorrecto() {
        //GIVEN
        String email = addUsuarioBD();
        UsuarioData usuario = usuarioService.findByEmail("miproyecto@gmail.com");
        VerificarCuentaData verificarCuentaData = new VerificarCuentaData();
        verificarCuentaData.setCodigoActivacion(usuario.getCodigoActivacion());

        //WHEN
        boolean verificado = usuarioService.verificarCodigoActivacion(email, verificarCuentaData);

        //THEN
        UsuarioData usuarioVerificado = usuarioService.findByEmail("miproyecto@gmail.com");
        assertThat(verificado).isTrue();
        assertThat(usuarioVerificado.getActivo()).isTrue();

    }

    @Test
    public void servicioVerificarCuentaIncorrecto() {
        //GIVEN
        String email = addUsuarioBD();
        UsuarioData usuario = usuarioService.findByEmail("miproyecto@gmail.com");
        VerificarCuentaData verificarCuentaData = new VerificarCuentaData();
        verificarCuentaData.setCodigoActivacion("12345678");


        //WHEN
        boolean verificado = usuarioService.verificarCodigoActivacion(email, verificarCuentaData);

        //THEN
        UsuarioData usuarioVerificado = usuarioService.findByEmail("miproyecto@gmail.com");
        assertThat(verificado).isFalse();
        assertThat(usuarioVerificado.getActivo()).isFalse();
    }

    @Test
    public void servicioReenviarCodigoVerificacion() {
        //GIVEN
        String email = addUsuarioBD();
        UsuarioData usuario = usuarioService.findByEmail(email);
        String codigo = usuario.getCodigoActivacion();

        //WHEN
        usuarioService.reenviarCodigoVerificacion(email);

        //THEN
        UsuarioData nuevoUsuario = usuarioService.findByEmail(email);
        assertThat(nuevoUsuario.getCodigoActivacion()).isNotEqualTo(codigo);
    }

    @Test
    public void servicioReenviarCodigoVerificacionUsuarioNoEncontrado() {
        //GIVEN
        String email = "email@gmail.com";

        //WHEN & THEN
        assertThrows(UsuarioServiceException.class, () -> usuarioService.reenviarCodigoVerificacion(email), "Usuario no encontrado");
    }

    @Test
    public void servicioActualizarUsuario() {
        //GIVEN
        String email = addUsuarioBD();
        UsuarioData usuario = usuarioService.findByEmail(email);
        usuario.setUsername("miproyecto2");
        usuario.setPassword("miproyecto2");

        //WHEN
        usuarioService.actualizar(usuario);

        //THEN
        UsuarioData usuarioActualizado = usuarioService.findByEmail(email);
        assertThat(usuarioActualizado.getUsername()).isEqualTo("miproyecto2");
        assertThat(usuarioActualizado.getPassword()).isEqualTo("miproyecto2");
    }

    @Test
    public void servicioEncontrarUsuarioPorEmail() {
        //GIVEN
        String email = addUsuarioBD();

        //WHEN
        UsuarioData usuario = usuarioService.findByEmail(email);

        //THEN
        assertThat(usuario).isNotNull();
        assertThat(usuario.getEmail()).isEqualTo(email);
    }
}
