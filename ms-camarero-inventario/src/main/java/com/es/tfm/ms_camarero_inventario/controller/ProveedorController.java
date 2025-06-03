package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.service.ProveedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Proveedor> getAll() {
        return service.getAll();
    }

    @GetMapping("/bar/{barId}")
    public List<Proveedor> getByBarId(@PathVariable Integer barId) {
        return service.getByBarId(barId);
    }

    @GetMapping("/{id}")
    public Proveedor getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Proveedor create(@RequestBody Proveedor proveedor) {
        return service.create(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor update(@PathVariable Integer id, @RequestBody Proveedor proveedor) {
        return service.update(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}