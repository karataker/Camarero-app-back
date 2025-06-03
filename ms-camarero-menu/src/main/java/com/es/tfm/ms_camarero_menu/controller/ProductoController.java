package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.Producto;
import com.es.tfm.ms_camarero_menu.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAll() {
        return productoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Integer id) {
        Producto producto = productoService.getById(id);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/bar/{barId}")
    public List<Producto> getByBar(@PathVariable Integer barId) {
        return productoService.getByBar(barId);
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productoService.delete(id);
    }

    @GetMapping("/{id}/raciones-disponibles")
    public ResponseEntity<Integer> calcularRaciones(@PathVariable Integer id) {
        Producto producto = productoService.getById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        int raciones = productoService.calcularRacionesDisponibles(producto);
        return ResponseEntity.ok(raciones);
    }

}