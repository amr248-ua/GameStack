package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/")
    public String home(Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            UsuarioData usuario = usuarioService.findByUsername(username); // Buscamos el usuario en la BD

            if (usuario != null && !usuario.getActivo()) {
                redirectAttributes.addFlashAttribute("email", usuario.getEmail());
                return "redirect:/verificar-cuenta";
            }
        }
        model.addAttribute("title", "Página Principal");
        return "home";
    }
}
