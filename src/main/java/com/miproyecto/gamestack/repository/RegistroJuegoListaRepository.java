package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.RegistroJuegoLista;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RegistroJuegoListaRepository extends CrudRepository<RegistroJuegoLista, Long> {
    Optional<List<RegistroJuegoLista>> findByUsuarioId(Long usuarioId);
}
