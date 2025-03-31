package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.GeneroData;
import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.repository.GeneroRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<GeneroData> obtenerGeneros() {
        List<Genero> generos = generoRepository.findAll();
        return generos.stream()
                .map(genero -> modelMapper.map(genero, GeneroData.class))
                .collect(Collectors.toList());
    }
}
