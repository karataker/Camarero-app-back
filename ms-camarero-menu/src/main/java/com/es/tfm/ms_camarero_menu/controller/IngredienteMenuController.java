package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.service.IngredienteMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/ingredientes")
@CrossOrigin
public class IngredienteMenuController {

    private final IngredienteMenuService service;

    public IngredienteMenuController(IngredienteMenuService service) {
        this.service = service;
    }

    @GetMapping("/producto/{productoId}")
    public List<IngredienteMenu> getByProducto(@PathVariable int productoId) {
        return service.getByProductoMenuId(productoId);
    }

    @PostMapping
    public IngredienteMenu create(@RequestBody IngredienteMenu ingrediente) {
        return service.create(ingrediente);
    }

    @PutMapping("/{id}")
    public IngredienteMenu update(@PathVariable Integer id, @RequestBody IngredienteMenu ingrediente) {
        return service.update(id, ingrediente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}