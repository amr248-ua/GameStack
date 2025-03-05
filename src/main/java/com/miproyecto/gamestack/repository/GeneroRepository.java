package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Genero;
import org.springframework.data.repository.CrudRepository;

public interface GeneroRepository extends CrudRepository<Genero, Long> {
    // Aquí se pueden agregar métodos personalizados
    public Genero findByGenero(String genero);
}
