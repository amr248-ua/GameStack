package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Plataforma;
import org.springframework.data.repository.CrudRepository;

public interface PlataformaRepository extends CrudRepository<Plataforma, Long> {
    // Aquí se pueden agregar métodos personalizados
    Plataforma findByPlataforma(String plataforma);
}
