package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Reseña;
import com.miproyecto.gamestack.model.Videojuego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReseñaRepository extends CrudRepository<Reseña, Long> {
    Page<Reseña> findByVideojuegoId(Long videojuegoId, Pageable pageable);
    Optional<List<Reseña>> findByUsuarioId(Long usuarioId);
    @Query("SELECT r.videojuego FROM Reseña r WHERE r.usuario.id = :usuarioId AND r.recomienda = true")
    List<Videojuego> findVideojuegosRecomendadosPorUsuario(@Param("usuarioId") Long usuarioId);
}
