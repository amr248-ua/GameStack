package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.*;
import com.miproyecto.gamestack.service.GeneroService;
import com.miproyecto.gamestack.service.PlataformaService;
import com.miproyecto.gamestack.service.ReseñaService;
import com.miproyecto.gamestack.service.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class VideojuegoController {
    @Autowired
    VideojuegoService videojuegoService;
    @Autowired
    GeneroService generoService;
    @Autowired
    PlataformaService plataformaService;
    @Autowired
    ReseñaService reseñaService;

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

        model.addAttribute("paginaVideojuegos", paginaVideojuegos);
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaVideojuegos.getTotalPages());
        model.addAttribute("titulo", titulo);
        model.addAttribute("genero", genero);
        model.addAttribute("plataforma", plataforma);
        model.addAttribute("ordenarPor", ordenarPor);

        return "listaVideojuegos";
    }

    @GetMapping("/videojuego/{id}")
    public String verVideojuego(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@ModelAttribute("error") String error, @ModelAttribute("mensaje") String mensaje) {
        VideojuegoData videojuego = videojuegoService.obtenerVideojuegoPorId(id);
        Page<ReseñaData> reseñas = reseñaService.obtenerReseñasPorVideojuegoId(id, page, size);
        // Calcular el porcentaje de reseñas positivas
        int porcentajeReseñasPositivas = reseñaService.porcentajeReseñasPositivas(reseñas);

        //Para contener la reseña que se va a crear
        ReseñaData reseña = new ReseñaData();
        RegistroJuegoListaData registroJuegoListaData = new RegistroJuegoListaData();
        if (videojuego != null) {
            model.addAttribute("videojuego", videojuego);
            model.addAttribute("paginaActual", page);
            model.addAttribute("reseñas", reseñas);
            model.addAttribute("registroJuegoListaData", registroJuegoListaData);
            model.addAttribute("totalPaginas", reseñas.getTotalPages());
            model.addAttribute("reseña", reseña);
            model.addAttribute("error", error);
            model.addAttribute("mensaje", mensaje);
            model.addAttribute("porcentajeReseñasPositivas", porcentajeReseñasPositivas);
            return "detalleVideojuego";
        } else {
            return "redirect:/videojuego/buscar";
        }
    }

    @PostMapping("/videojuego/{id}/reseña")
    public String crearReseña(@PathVariable Long id, @Valid ReseñaData reseñaData, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().getFirst().getDefaultMessage());
            redirectAttributes.addFlashAttribute("reseñaData", reseñaData);
            return "redirect:/videojuego/" + id;
        }
        // Asignar el videojuego a la reseña y al usuario logueado
        VideojuegoData videojuegoData = videojuegoService.obtenerVideojuegoPorId(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (videojuegoData != null && auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            try{
                reseñaService.crearReseña(reseñaData, String.valueOf(videojuegoData.getId()), username);
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/videojuego/" + id;
            }
            return "redirect:/videojuego/" + id;
        } else {
            return "redirect:/videojuego/buscar";
        }
    }

}
