package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Plataforma;
import com.miproyecto.gamestack.model.Tag;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.GeneroRepository;
import com.miproyecto.gamestack.repository.PlataformaRepository;
import com.miproyecto.gamestack.repository.TagRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
// Se ejecuta solo si el perfil activo es 'dev'
@Profile("dev")
public class InitDbService {
    @Autowired
    private RawgService rawgService;
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PlataformaRepository plataformaRepository;
    @Autowired
    private TagRepository tagRepository;

    @PostConstruct
    public void initDatabase() {
        System.out.println("Cargando datos desde la API RAWG...");
        rawgService.cargarJuegosDesdeRAWG();
        System.out.println("Datos cargados correctamente en la base de datos H2.");
    }

    //@PostConstruct
    public void init() {
        // Aquí puedes inicializar la base de datos con datos de prueba
        // Por ejemplo, crear algunos videojuegos y plataformas
        Plataforma plataforma1 = new Plataforma("PC");
        Plataforma plataforma2 = new Plataforma("PS5");
        Plataforma plataforma3 = new Plataforma("Xbox Series X");

        Genero genero1 = new Genero("Acción");
        Genero genero2 = new Genero("Aventura");

        Tag tag1 = new Tag("Multijugador");
        Tag tag2 = new Tag("Aventura");

        Videojuego videojuego1 = new Videojuego("Juego 1", "Descripción del juego 1", "imagen1.jpg", LocalDate.now());
        videojuegoRepository.save(videojuego1);
        videojuego1.addPlataforma(plataforma1);
        videojuego1.addGenero(genero1);
        videojuego1.addTag(tag1);
        videojuego1.addTag(tag2);
        plataforma1.addVideojuego(videojuego1);
        genero1.addVideojuegos(videojuego1);
        tag1.addVideojuegos(videojuego1);
        tag2.addVideojuegos(videojuego1);

        Videojuego videojuego2 = new Videojuego("Juego 2", "Descripción del juego 2", "imagen2.jpg", LocalDate.now());
        videojuegoRepository.save(videojuego2);
        videojuego2.addPlataforma(plataforma2);
        videojuego2.addGenero(genero2);
        videojuego2.addGenero(genero1);
        videojuego2.addTag(tag1);
        plataforma2.addVideojuego(videojuego2);
        genero2.addVideojuegos(videojuego2);
        genero1.addVideojuegos(videojuego2);
        tag2.addVideojuegos(videojuego2);

        plataformaRepository.save(plataforma1);
        plataformaRepository.save(plataforma2);
        plataformaRepository.save(plataforma3);
        generoRepository.save(genero1);
        generoRepository.save(genero2);
        tagRepository.save(tag1);
        tagRepository.save(tag2);

        videojuegoRepository.save(videojuego1);
        videojuegoRepository.save(videojuego2);

    }


}
