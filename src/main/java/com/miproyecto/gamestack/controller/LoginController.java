package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.dto.VerificarCuentaData;
import com.miproyecto.gamestack.service.EmailService;
import com.miproyecto.gamestack.service.UsuarioService;
import com.miproyecto.gamestack.service.UsuarioServiceException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

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


}
