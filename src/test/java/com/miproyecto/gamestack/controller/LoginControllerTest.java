package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.dto.VerificarCuentaData;
import com.miproyecto.gamestack.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    public void registroUsuarioOK() {
        //GIVEN
        RegistroData usuario = new RegistroData();
        usuario.setEmail("test@test.com");
        usuario.setUsername("test");
        usuario.setPassword("test");
        LocalDate fecha = LocalDate.of(2002, 7, 19);
        usuario.setFechaNacimiento(fecha);

        //WHEN
        when(usuarioService.registrar(usuario)).thenReturn(new UsuarioData());

        //THEN
        try {
            this.mockMvc.perform(post("https://localhost:8443/registro")
                    .param("email", "test@test.com")
                    .param("user", "test")
                    .param("password1", "test")
                    .param("password2", "test")
                    .param("fechaNacimiento", "2002-07-19"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/verificar-cuenta"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verificarCuentaOK(){
        //GIVEN
        VerificarCuentaData verificarCuentaData = new VerificarCuentaData();
        verificarCuentaData.setEmail("test@test.com");
        verificarCuentaData.setCodigoActivacion("12345678");

        //WHEN
        when(usuarioService.verificarCodigoActivacion(eq("test@test.com"), any(VerificarCuentaData.class)))
                .thenReturn(true);


        //THEN
        try {
            this.mockMvc.perform(post("https://localhost:8443/verificar-cuenta")
                    .param("email", "test@test.com")
                    .param("codigoActivacion", "12345678"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));//Si la verificacion de codigo devuelve true se debe comprobar que redirige a /
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void verificarCuentaError() {
        //GIVEN
        VerificarCuentaData verificarCuentaData = new VerificarCuentaData();
        verificarCuentaData.setEmail("test@test.com");
        verificarCuentaData.setCodigoActivacion("12345678");

        //WHEN
        when(usuarioService.verificarCodigoActivacion(eq("test@test.com"), any(VerificarCuentaData.class)))
                .thenReturn(false);

        //THEN
        try {
            this.mockMvc.perform(post("https://localhost:8443/verificar-cuenta")
                            .param("email", "test@test.com")
                            .param("codigoActivacion", "12345678"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/verificar-cuenta"));//Si la verificacion de codigo devuelve false se debe comprobar que redirige a verificar-cuenta
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
