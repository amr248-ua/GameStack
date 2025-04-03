package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Videojuego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


public interface VideojuegoRepository extends CrudRepository<Videojuego, Long> {
    // Aquí se pueden agregar métodos personalizados
    Optional<Videojuego> findVideojuegoById(Long id);
    Optional<Videojuego> findByTitulo(String titulo);
    Page<Videojuego> findAll(Pageable pageable);
    Page<Videojuego> findByTituloContainingIgnoreCase(String nombre, Pageable pageable);
    @Query("SELECT v FROM Videojuego v JOIN v.generos g WHERE g.genero = :nombreGenero")
    Page<Videojuego> findByGenero(@Param("nombreGenero") String nombreGenero, Pageable pageable);
    @Query("SELECT v FROM Videojuego v JOIN v.plataformas p WHERE p.plataforma = :nombrePlataforma")
    Page<Videojuego> findByPlataforma(@Param("nombrePlataforma") String nombrePlataforma, Pageable pageable);
    @Query("SELECT v FROM Videojuego v order by v.fechaLanzamiento asc")
    Page<Videojuego> findAllByFechaLanzamientoOrderByFechaLanzamientoAsc(Pageable pageable);
    @Query("SELECT v FROM Videojuego v order by v.fechaLanzamiento desc")
    Page<Videojuego> findAllByFechaLanzamientoOrderByFechaLanzamientoDesc(Pageable pageable);

}
