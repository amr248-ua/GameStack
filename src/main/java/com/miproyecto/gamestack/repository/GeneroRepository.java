package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Videojuego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeneroRepository extends CrudRepository<Genero, Long> {
    // Aquí se pueden agregar métodos personalizados
    Genero findByGenero(String genero);
    List<Genero> findAll();
}
