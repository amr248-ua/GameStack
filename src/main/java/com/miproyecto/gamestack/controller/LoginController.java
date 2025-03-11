package com.miproyecto.gamestack.controller;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.dto.VerificarCuentaData;
import com.miproyecto.gamestack.service.EmailService;
import com.miproyecto.gamestack.service.UsuarioService;
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
    @Autowired
    private EmailService emailService;

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

        if (usuarioService.findByEmail(registroData.getEmail()) != null) {
            model.addAttribute("registroData", registroData);
            model.addAttribute("error", "El usuario " + registroData.getEmail() + " ya existe");
            return "formRegistro";
        }

        UsuarioData usuario = new UsuarioData();
        usuario.setEmail(registroData.getEmail());
        usuario.setPassword(registroData.getPassword());
        usuario.setFechaNacimiento(registroData.getFechaNacimiento());
        usuario.setUsername(registroData.getUsername());

        String codigoActivacion = emailService.generarCodigo(8);
        usuario.setCodigoActivacion(codigoActivacion);

        usuarioService.registrar(usuario);

        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("user", registroData.getUsername());
            variables.put("codigo", codigoActivacion);

            emailService.enviarCorreoConTemplate(registroData.getEmail(), "Verificar cuenta", variables);


        } catch (MessagingException e) {
            return "Error al enviar el correo: " + e.getMessage();
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

        UsuarioData usuario = usuarioService.findByEmail(email);

        if (usuario == null) {
            return "redirect:/registro";
        }

        if(usuario.getCodigoActivacion().equals(verificarCuentaData.getCodigoActivacion())) {
            usuario.setActivo(true);
            usuarioService.actualizar(usuario);
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("email", email);
        redirectAttributes.addFlashAttribute("error", "Codigo introducido incorrecto, revisa tu correo");
        return "redirect:/verificar-cuenta";
    }


}
