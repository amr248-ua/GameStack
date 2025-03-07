package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Plataforma;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.GeneroRepository;
import com.miproyecto.gamestack.repository.PlataformaRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
// Se ejecuta solo si el perfil activo es 'dev'
@Profile("dev")
public class InitDbService {
    @Autowired
    private RawgService rawgService;

    @PostConstruct
    public void initDatabase() {
        System.out.println("Cargando datos desde la API RAWG...");
        rawgService.cargarJuegosDesdeRAWG();
        System.out.println("Datos cargados correctamente en la base de datos H2.");
    }
}
