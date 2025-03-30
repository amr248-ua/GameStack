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
}
