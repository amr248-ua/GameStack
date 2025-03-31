package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.PlataformaData;
import com.miproyecto.gamestack.repository.PlataformaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlataformaService {
    @Autowired
    private PlataformaRepository plataformaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<PlataformaData> obtenerPlataformas() {
        List<com.miproyecto.gamestack.model.Plataforma> plataformas = plataformaRepository.findAll();
        return plataformas.stream()
                .map(plataforma -> modelMapper.map(plataforma, PlataformaData.class))
                .toList();
    }
}
