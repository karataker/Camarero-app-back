package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.model.dto.IngredienteDTO;
import com.es.tfm.ms_camarero_inventario.service.ProductoInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventario")
public class ProductoInventarioController {
  
    private final ProductoInventarioService service;

    public ProductoInventarioController(ProductoInventarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoInventario> getAll() {
        return service.getAll();
    }

    @GetMapping("/bar/{barId}")
    public List<ProductoInventario> getByBar(@PathVariable Integer barId) {
        return service.getByBar(barId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoInventario> getById(@PathVariable Integer id) {
        ProductoInventario p = service.getById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProductoInventario> getByCategoria(@PathVariable String categoria) {
        return service.getByCategoriaNombre(categoria);
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<Double> getStock(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getStockActual(id));
    }

    @PostMapping
    public ProductoInventario create(@RequestBody ProductoInventario producto) {
        return service.create(producto);
    }

    @PutMapping("/{id}")
    public ProductoInventario update(@PathVariable Integer id, @RequestBody ProductoInventario producto) {
        producto.setId(id);
        return service.update(producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PostMapping("/cantidad-raciones")
    public ResponseEntity<Integer> calcularRaciones(@RequestBody List<IngredienteDTO> ingredientes) {
        return ResponseEntity.ok(service.calcularRaciones(ingredientes));
    }

    @GetMapping("/bajo-minimo")
    public List<ProductoInventario> getProductosBajoMinimo() {
        return service.getBajoMinimo();
    }

    @PostMapping("/{id}/ajustar/entrada")
    public ResponseEntity<ProductoInventario> ajustarEntrada(@PathVariable Integer id, @RequestBody Map<String, Double> body) {
        Double cantidad = body.get("cantidad");
        return ResponseEntity.ok(service.ajustarStock(id, cantidad));
    }

    @PostMapping("/{id}/ajustar/salida")
    public ResponseEntity<ProductoInventario> ajustarSalida(@PathVariable Integer id, @RequestBody Map<String, Double> body) {
        Double cantidad = body.get("cantidad");
        return ResponseEntity.ok(service.ajustarStock(id, -cantidad));
    }
    
    @PostMapping("/restar-ingredientes/{productoId}")
    public ResponseEntity<Void> restarIngredientes(@PathVariable Integer productoId, @RequestParam int cantidad) {
        service.restarIngredientesPorProducto(productoId, cantidad);
        return ResponseEntity.ok().build();
    }

}
