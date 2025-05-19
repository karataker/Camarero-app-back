package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.Producto;
import com.es.tfm.ms_camarero_menu.service.ProductoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/bares/{barId}/menu")
public class ProductoController {

    private final ProductoService productoService;
    private final RestTemplate restTemplate;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping
    public List<Producto> obtenerMenu(@PathVariable Long barId) {
        return productoService.getMenuByBar(barId);
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long barId,
                                           @RequestBody Producto producto) {
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado");

        if (!barExiste(barId)) return ResponseEntity.status(404).body("Bar no encontrado");

        producto.setBarId(barId);
        return ResponseEntity.ok(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarProducto(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable Long id,
                                            @RequestBody Producto producto) {
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado");

        if (!barExiste(producto.getBarId())) return ResponseEntity.status(404).body("Bar no encontrado");

        return ResponseEntity.ok(productoService.save(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable Long id) {
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado");
        productoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private boolean esAdmin(String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();
                return "ADMIN".equals(claims.get("rol"));
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean barExiste(Long barId) {
        try {
            String url = "http://localhost:8082/api/bares/" + barId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }
}