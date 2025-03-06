package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
    // Aquí se pueden agregar métodos personalizados
    public Tag findByTag(String tag);
}
