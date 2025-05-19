package com.es.tfm.ms_camarero_usuarios.service;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import com.es.tfm.ms_camarero_usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> autenticar(String matricula, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByMatricula(matricula);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public Optional<Usuario> getUsuarioByMatricula(String matricula) {
        return usuarioRepository.findByMatricula(matricula);
    }
}