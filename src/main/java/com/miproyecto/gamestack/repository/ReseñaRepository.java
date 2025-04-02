package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Reseña;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReseñaRepository extends CrudRepository<Reseña, Long> {
    Page<Reseña> findByVideojuegoId(Long videojuegoId, Pageable pageable);
    Optional<List<Reseña>> findByUsuarioId(Long usuarioId);
}
