package com.es.tfm.ms_camarero_usuarios.security;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "clave_secreta_segura_para_el_token";

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getMatricula())
                .claim("rol", usuario.getRol().getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}