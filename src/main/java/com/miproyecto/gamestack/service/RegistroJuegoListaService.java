package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RegistroJuegoListaData;
import com.miproyecto.gamestack.model.RegistroJuegoLista;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.RegistroJuegoListaRepository;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroJuegoListaService {
    @Autowired
    private RegistroJuegoListaRepository registroJuegoListaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void crearRegistroJuegoLista(RegistroJuegoListaData registroJuegoListaData, String videojuegoId, String username) throws RegistroJuegoListaException{
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        Videojuego videojuego = videojuegoRepository.findById(Long.valueOf(videojuegoId)).orElse(null);

        if (usuario != null && videojuego != null) {
            // Verificar si el registro ya existe
            List<RegistroJuegoLista> existingRegistro = registroJuegoListaRepository.findByUsuarioId(usuario.getId())
                    .orElse(null);

            if (existingRegistro != null) {
                for (RegistroJuegoLista registro : existingRegistro) {
                    if (registro.getVideojuego().getId().equals(videojuego.getId())) {
                        throw new RegistroJuegoListaException("El videojuego ya está registrado en la lista del usuario.");
                    }
                }
            }

            RegistroJuegoLista registroJuegoLista = modelMapper.map(registroJuegoListaData, RegistroJuegoLista.class);
            registroJuegoLista.setUsuario(usuario);
            registroJuegoLista.setVideojuego(videojuego);
            registroJuegoListaRepository.save(registroJuegoLista);
        }
    }

}
