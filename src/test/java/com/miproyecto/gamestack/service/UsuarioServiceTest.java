package com.miproyecto.gamestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;
}
