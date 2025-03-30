package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.VideojuegoData;
import com.miproyecto.gamestack.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideojuegoController {
    @Autowired
    VideojuegoService videojuegoService;

    @GetMapping("/videojuego/buscar")
    public String listaVideojuegos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VideojuegoData> paginaVideojuegos = videojuegoService.obtenerVideojuegosPaginados(pageable);

        model.addAttribute("paginaVideojuegos", paginaVideojuegos);
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaVideojuegos.getTotalPages());

        return "listaVideojuegos"; // Nombre de la vista Thymeleaf
    }

}
