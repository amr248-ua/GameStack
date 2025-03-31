package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.VideojuegoData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.miproyecto.gamestack.repository.VideojuegoRepository;

import java.time.LocalDate;

@Service
public class VideojuegoService {
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Page<VideojuegoData> obtenerVideojuegosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videojuegoRepository.findAll(pageable)
            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
    }

    @Transactional
    public Page<VideojuegoData> buscarVideojuegosPorNombre(String nombre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videojuegoRepository.findByTituloContainingIgnoreCase(nombre, pageable)
            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
    }

    @Transactional
    public Page<VideojuegoData> buscarVideojuegosPorGenero(String nombreGenero, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videojuegoRepository.findByGenero(nombreGenero, pageable)
            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
    }

    @Transactional
    public Page<VideojuegoData> buscarVideojuegosPorPlataforma(String nombrePlataforma, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videojuegoRepository.findByPlataforma(nombrePlataforma, pageable)
            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
    }

    @Transactional
    public Page<VideojuegoData> buscarVideojuegosOrdenarPor(String ordenarPor, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return switch (ordenarPor) {
            case "fechaLanzamientoAsc" ->
                    videojuegoRepository.findAllByFechaLanzamientoOrderByFechaLanzamientoAsc(pageable)
                            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
            case "fechaLanzamientoDesc" ->
                    videojuegoRepository.findAllByFechaLanzamientoOrderByFechaLanzamientoDesc(pageable)
                            .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
            default -> videojuegoRepository.findAll(pageable)
                    .map(videojuego -> modelMapper.map(videojuego, VideojuegoData.class));
        };
    }

}
