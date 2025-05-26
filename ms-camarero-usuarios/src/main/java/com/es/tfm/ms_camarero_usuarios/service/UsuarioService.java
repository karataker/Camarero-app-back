package com.es.tfm.ms_camarero_usuarios.service;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import com.es.tfm.ms_camarero_usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        // Cifrar la contraseña antes de guardar
        String hashed = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(hashed);
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerPorBar(int barId) {
        return usuarioRepository.findByBarId(barId);
    }
}
