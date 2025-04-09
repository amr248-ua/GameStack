package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.RegistroJuegoListaData;
import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.dto.VerificarCuentaData;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ReseñaService reseñaService;
    @Autowired
    RegistroJuegoListaService registroJuegoListaService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("registroData", new RegistroData());
        return "formRegistro";
    }

    @PostMapping("/registro")
    public String registroSubmit(@Valid RegistroData registroData, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "formRegistro";
        }

        try{
            usuarioService.registrar(registroData);
        } catch (UsuarioServiceException e) {
            model.addAttribute("registroData", registroData);
            model.addAttribute("error", e.getMessage());

            return "formRegistro";
        }

        redirectAttributes.addFlashAttribute("email", registroData.getEmail());
        return "redirect:/verificar-cuenta";
    }

    @GetMapping("/verificar-cuenta")
    public String verificarCuentaForm(Model model, @ModelAttribute("email") String email, @ModelAttribute("error") String error) {
        model.addAttribute("verificarCuentaData", new VerificarCuentaData());
        model.addAttribute("email", email);
        model.addAttribute("error", error);
        return "formVerificarCuenta";
    }

    @PostMapping("/verificar-cuenta")
    public String verificarCuenta(VerificarCuentaData verificarCuentaData, String email, RedirectAttributes redirectAttributes, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "formVerificarCuenta";
        }

        if (!usuarioService.verificarCodigoActivacion(email, verificarCuentaData)) {
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("error", "Codigo introducido incorrecto, revisa tu correo");
            return "redirect:/verificar-cuenta";
        }

        return "redirect:/";

    }

    @PostMapping("/reenviar-codigo")
    public String reenviarCodigo(String email, RedirectAttributes redirectAttributes) {
        try{
            usuarioService.reenviarCodigoVerificacion(email);
        }catch (UsuarioServiceException e) {
            return "redirect:/registro";
        }

        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/verificar-cuenta";
    }

    @GetMapping("/usuario/{username}")
    public String verPerfil(Model model, @PathVariable String username,@ModelAttribute("error") String error) {
        UsuarioData usuario = usuarioService.findByUsername(username);
        List<Videojuego> juegosRecomendados = reseñaService.obtenerJuegosRecomendados(usuario.getId());


        boolean editandoMiPerfil = false;

        if(username.equals(usuarioService.obtenerUsuarioLogueado().getUsername())) {
            editandoMiPerfil = true;
        }


        model.addAttribute("error", error);
        model.addAttribute("juegosRecomendados", juegosRecomendados);
        model.addAttribute("editandoMiPerfil", editandoMiPerfil);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/usuario/{username}/lista/añadir/{videojuegoId}")
    public String añadirAMiLista(@PathVariable String username,@PathVariable String videojuegoId , @Valid RegistroJuegoListaData registroJuegoListaData, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        try {
            registroJuegoListaService.crearRegistroJuegoLista(registroJuegoListaData, videojuegoId, username);
            redirectAttributes.addFlashAttribute("mensaje", "Juego añadido a la lista correctamente.");
        } catch (RegistroJuegoListaException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("registroJuegoListaData", registroJuegoListaData);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/mi-perfil/actualizar-biografia")
    public String actualizarBiografia(@Valid UsuarioData editarUsuario,BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().getFirst().getDefaultMessage());
            redirectAttributes.addFlashAttribute("usuario", editarUsuario);
            return "redirect:/usuario/" + usuarioService.obtenerUsuarioLogueado().getUsername();
        }

        editarUsuario.setUsername(usuarioService.obtenerUsuarioLogueado().getUsername());
        usuarioService.actualizar(editarUsuario);

        redirectAttributes.addFlashAttribute("mensaje", "Biografía actualizada correctamente.");
        return "redirect:/usuario/" + editarUsuario.getUsername();
    }

    @PostMapping("/mi-perfil/actualizar-foto")
    public String actualizarFotoPerfil(@RequestParam("fotoPerfil") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            if(file.getSize() > 1_000_000) {
                redirectAttributes.addFlashAttribute("error", "El tamaño de la foto es demasiado grande");
                return "redirect:/usuario/" + usuarioService.obtenerUsuarioLogueado().getUsername();
            }

            try {
                UsuarioData usuario = usuarioService.obtenerUsuarioLogueado();
                usuario.setFotoPerfil(file.getBytes());

                usuarioService.actualizar(usuario);

                return "redirect:/usuario/" + usuario.getUsername();

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/usuario/" + usuarioService.obtenerUsuarioLogueado().getUsername();
            }
        }
        return "redirect:/usuario/" + usuarioService.obtenerUsuarioLogueado().getUsername();
    }

    @GetMapping("/perfil/foto/{username}")
    @ResponseBody
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable String username) {
        UsuarioData usuario = usuarioService.findByUsername(username);

        if (usuario.getFotoPerfil() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(usuario.getFotoPerfil());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
