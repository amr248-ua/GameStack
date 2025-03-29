package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RegistroData;
import com.miproyecto.gamestack.dto.UsuarioData;
import com.miproyecto.gamestack.dto.VerificarCuentaData;
import com.miproyecto.gamestack.model.Usuario;
import com.miproyecto.gamestack.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Transactional
    public UsuarioData registrar(RegistroData registroData) throws UsuarioServiceException{
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(registroData.getEmail());
        Optional<Usuario> usuarioBD2 = usuarioRepository.findByUsername(registroData.getUsername());
        if (usuarioBD.isPresent())
            throw new UsuarioServiceException("El usuario con email " + registroData.getEmail() + " ya está registrado");
        else if (usuarioBD2.isPresent())
            throw new UsuarioServiceException("El usuario con nombre de usuario " + registroData.getUsername() + " ya está registrado");
        else {
            Usuario usuario = new Usuario();
            usuario.setEmail(registroData.getEmail());
            usuario.setPassword(passwordEncoder.encode(registroData.getPassword()));
            usuario.setFechaNacimiento(registroData.getFechaNacimiento());
            usuario.setUsername(registroData.getUsername());

            String codigoActivacion = emailService.generarCodigo(8);
            usuario.setCodigoActivacion(codigoActivacion);

            usuarioRepository.save(usuario);

            try {
                Map<String, Object> variables = new HashMap<>();
                variables.put("user", registroData.getUsername());
                variables.put("codigo", codigoActivacion);

                emailService.enviarCorreoConTemplate(registroData.getEmail(), "Verificar cuenta", variables);


            } catch (MessagingException e) {
                System.out.println("Error al enviar el correo: " + e.getMessage());
            }

            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    @Transactional
    public boolean verificarCodigoActivacion(String email, VerificarCuentaData verificarCuentaData) {
        UsuarioData usuario = findByEmail(email);
        if (usuario == null) return false;
        else {
            if(usuario.getCodigoActivacion().equals(verificarCuentaData.getCodigoActivacion())) {
                usuario.setActivo(true);
                actualizar(usuario);
                return true;
            }

            return false;
        }
    }

    @Transactional
    public void reenviarCodigoVerificacion(String email) throws UsuarioServiceException{
        UsuarioData usuario = findByEmail(email);

        if (usuario == null) {
            throw new UsuarioServiceException("Usuario no encontrado");
        }

        String codigoActivacion = emailService.generarCodigo(8);
        usuario.setCodigoActivacion(codigoActivacion);
        actualizar(usuario);

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", usuario.getUsername());
        variables.put("codigo", usuario.getCodigoActivacion());

        try {
            emailService.enviarCorreoConTemplate(email, "Verificar cuenta", variables);
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    @Transactional
    public UsuarioData actualizar(UsuarioData usuario) {
        if (usuario.getEmail() == null)
            throw new UsuarioServiceException("El usuario no tiene email");
        else if (usuario.getPassword() == null)
            throw new UsuarioServiceException("El usuario no tiene password");
        else {
            Usuario usuarioNuevo = modelMapper.map(usuario, Usuario.class);
            usuarioNuevo = usuarioRepository.save(usuarioNuevo);
            return modelMapper.map(usuarioNuevo, UsuarioData.class);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioData findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioData findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }
}
