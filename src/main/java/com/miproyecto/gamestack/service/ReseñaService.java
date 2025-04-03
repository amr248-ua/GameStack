package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.ReseñaData;
import com.miproyecto.gamestack.model.Reseña;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.repository.ReseñaRepository;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReseñaService {
    @Autowired
    private ReseñaRepository reseñaRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Page<ReseñaData> obtenerReseñasPorVideojuegoId(Long videojuegoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reseñaRepository.findByVideojuegoId(videojuegoId, pageable)
            .map(reseña -> modelMapper.map(reseña, ReseñaData.class));
    }

    @Transactional
    public void crearReseña(ReseñaData reseñaData, String videojuegoId, String username) throws ReseñaServiceException{
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario != null) {
            List<Reseña> reseñasExistentes = reseñaRepository.findByUsuarioId(usuario.getId()).orElse(null);

            if (reseñasExistentes != null && !reseñasExistentes.isEmpty()) {
                for (Reseña reseña : reseñasExistentes) {
                    if (reseña.getVideojuego().getId().equals(Long.parseLong(videojuegoId))) {
                        throw new ReseñaServiceException("El usuario ya ha creado una reseña para este videojuego.");
                    }
                }
            }

            Reseña reseña = modelMapper.map(reseñaData, Reseña.class);
            reseña.setActivo(true);
            reseña.setFechaCreacion(new java.util.Date());
            reseña.setVideojuego(videojuegoRepository.findById(Long.parseLong(videojuegoId)).orElse(null));
            reseña.setUsuario(usuario);
            reseñaRepository.save(reseña);
        }
    }

    public int porcentajeReseñasPositivas(Page<ReseñaData> reseñas) {
        int totalReseñas = reseñas.getNumberOfElements();
        int reseñasPositivas = 0;

        for (ReseñaData reseña : reseñas) {
            if (reseña.getRecomienda()) {
                reseñasPositivas++;
            }
        }

        return (int) ((double) reseñasPositivas / totalReseñas * 100);
    }
}
