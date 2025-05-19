package com.es.tfm.ms_camarero_usuarios.controller;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import com.es.tfm.ms_camarero_usuarios.service.AuthService;
import com.es.tfm.ms_camarero_usuarios.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> login) {
        String matricula = login.get("matricula");
        String contrasena = login.get("contrasena");

        return authService.autenticar(matricula, contrasena)
                .map(usuario -> {
                    String token = jwtUtil.generateToken(usuario.getMatricula(), usuario.getRol().getNombre());
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas")));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Token faltante o mal formado"));
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseToken(token);
            String matricula = claims.getSubject();

            return authService.getUsuarioByMatricula(matricula)
                    .map(usuario -> {
                        String nuevoToken = jwtUtil.generateToken(usuario.getMatricula(), usuario.getRol().getNombre());
                        return ResponseEntity.ok(Map.of("token", nuevoToken));
                    })
                    .orElse(ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado")));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
        }
    }
}