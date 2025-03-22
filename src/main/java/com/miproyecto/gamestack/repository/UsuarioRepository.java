package com.miproyecto.gamestack.repository;

import com.miproyecto.gamestack.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String s);
    Optional<Usuario> findByUsername(String s);
}
