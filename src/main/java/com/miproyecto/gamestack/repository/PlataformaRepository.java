package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Plataforma;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlataformaRepository extends CrudRepository<Plataforma, Long> {
    // Aquí se pueden agregar métodos personalizados
    Plataforma findByPlataforma(String plataforma);
    List<Plataforma> findAll();
}
