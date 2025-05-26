package com.es.tfm.ms_camarero_usuarios.controller;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import com.es.tfm.ms_camarero_usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bar/{barId}")
    public ResponseEntity<List<Usuario>> obtenerPorBar(@PathVariable int barId) {
        return ResponseEntity.ok(usuarioService.obtenerPorBar(barId));
    }
}
