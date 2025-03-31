package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.GeneroData;
import com.miproyecto.gamestack.dto.PlataformaData;
import com.miproyecto.gamestack.dto.VideojuegoData;
import com.miproyecto.gamestack.service.GeneroService;
import com.miproyecto.gamestack.service.PlataformaService;
import com.miproyecto.gamestack.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VideojuegoController {
    @Autowired
    VideojuegoService videojuegoService;
    @Autowired
    GeneroService generoService;
    @Autowired
    PlataformaService plataformaService;

    @ModelAttribute("listaGeneros")//Añade el model atribute a todas las vistas
    public List<GeneroData> listaGeneros() {
        return generoService.obtenerGeneros();
    }

    @ModelAttribute("listaPlataformas")
    public List<PlataformaData> listaPlataformas() {
        return plataformaService.obtenerPlataformas();
    }

    @GetMapping("/videojuego/buscar")
    public String buscarVideojuegos(@RequestParam(required = false) String titulo, @RequestParam(required = false) String genero,
                                    @RequestParam(required = false) String plataforma, @RequestParam(required = false) String ordenarPor,
                                    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VideojuegoData> paginaVideojuegos;

        // Determinar qué filtro aplicar
        if (titulo != null && !titulo.isEmpty()) {
            paginaVideojuegos = videojuegoService.buscarVideojuegosPorNombre(titulo, page, size);
        } else if (genero != null && !genero.isEmpty()) {
            paginaVideojuegos = videojuegoService.buscarVideojuegosPorGenero(genero, page, size);
        } else if (plataforma != null && !plataforma.isEmpty()) {
            paginaVideojuegos = videojuegoService.buscarVideojuegosPorPlataforma(plataforma, page, size);
        } else if (ordenarPor != null && !ordenarPor.isEmpty()) {
            paginaVideojuegos = videojuegoService.buscarVideojuegosOrdenarPor(ordenarPor, page, size);
        } else {
            // Si no hay filtros, obtener todos los videojuegos
            paginaVideojuegos = videojuegoService.obtenerVideojuegosPaginados(page, size);
        }

        // Pasar atributos al modelo
        model.addAttribute("paginaVideojuegos", paginaVideojuegos);
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaVideojuegos.getTotalPages());
        model.addAttribute("titulo", titulo);
        model.addAttribute("genero", genero);
        model.addAttribute("plataforma", plataforma);
        model.addAttribute("ordenarPor", ordenarPor);

        return "listaVideojuegos";
    }

}
