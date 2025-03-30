package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Videojuego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface VideojuegoRepository extends CrudRepository<Videojuego, Long> {
    // Aquí se pueden agregar métodos personalizados
    Page<Videojuego> findAll(Pageable pageable);
}
