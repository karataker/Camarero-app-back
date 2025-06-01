package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventario")
public class ProductoInventarioController {

    private final ProductoInventarioRepository repo;

    public ProductoInventarioController(ProductoInventarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{barId}")
    public ResponseEntity<List<ProductoInventario>> getInventarioPorBar(@PathVariable Long barId) {
        List<ProductoInventario> inventario = repo.findByBarId(barId);
        return ResponseEntity.ok(inventario);
    }

    @PostMapping("/{barId}")
    public ResponseEntity<ProductoInventario> crearProducto(@PathVariable Long barId, @RequestBody ProductoInventario producto) {
        producto.setBarId(barId);
        ProductoInventario savedProducto = repo.save(producto);
        return ResponseEntity.ok(savedProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoInventario> actualizarProducto(@PathVariable Long id, @RequestBody ProductoInventario actualizado) {
        Optional<ProductoInventario> existingProducto = repo.findById(id);
        if (existingProducto.isPresent()) {
            actualizado.setId(id);
            ProductoInventario updatedProducto = repo.save(actualizado);
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}