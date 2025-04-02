package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RegistroData;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
// Se ejecuta solo si el perfil activo es 'dev'
@Profile("dev")
public class InitDbService {
    @Autowired
    private RawgService rawgService;
    @Autowired
    private UsuarioService usuarioService;


    @PostConstruct
    public void initDatabase() {
        System.out.println("Cargando datos desde la API RAWG...");
        rawgService.cargarJuegosDesdeRAWG();
        System.out.println("Datos cargados correctamente en la base de datos H2.");

        // Añadir usuario de pruebas a la base de datos
        RegistroData registroData = new RegistroData();
        registroData.setUsername("admin");
        registroData.setPassword("admin");
        registroData.setEmail("admin@admin.com");
        registroData.setActivo(true);
        registroData.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        usuarioService.registrar(registroData);


    }

}
