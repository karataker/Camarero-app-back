package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.Categoria;
import com.es.tfm.ms_camarero_menu.service.CategoriaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/categorias")

public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Obtener todas las categorías
    @GetMapping
    public List<Categoria> getAll() {
        return categoriaService.getAll();
    }

    // Obtener categorías por barId
    @GetMapping("/bar/{barId}")
    public List<Categoria> getByBar(@PathVariable Integer barId) {
        return categoriaService.getByBar(barId);
    }

    // Crear nueva categoría (opcional si permites desde el front)
    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) {
        return categoriaService.create(categoria);
    }

    // Actualizar categoría (opcional)
    @PutMapping("/{id}")
    public Categoria update(@PathVariable Integer id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriaService.update(id, categoria);
    }
}