package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.client.IngredienteMenuClient;
import com.es.tfm.ms_camarero_inventario.model.Categoria;
import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.model.dto.IngredienteDTO;
import com.es.tfm.ms_camarero_inventario.repository.CategoriaRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoInventarioService {

    private final ProductoInventarioRepository productoRepo;
    private final CategoriaRepository categoriaRepo;
    private final IngredienteMenuClient ingredienteMenuClient;

    public ProductoInventarioService(ProductoInventarioRepository productoRepo, CategoriaRepository categoriaRepo, IngredienteMenuClient ingredienteMenuClient) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
        this.ingredienteMenuClient = ingredienteMenuClient;
    }

    public List<ProductoInventario> getAll() {
        return productoRepo.findAll();
    }

    public ProductoInventario getById(Integer id) {
        return productoRepo.findById(id).orElse(null);
    }

    public List<ProductoInventario> getByBar(Integer barId) {
        return productoRepo.findByBarId(barId);
    }

    public List<ProductoInventario> getByCategoriaNombre(String nombreCategoria) {
        Categoria categoria = categoriaRepo.findByNombre(nombreCategoria);
        if (categoria == null) return List.of();
        return productoRepo.findByCategoria(categoria);
    }

    public Double getStockActual(Integer id) {
        ProductoInventario p = productoRepo.findById(id).orElse(null);
        return p != null ? p.getStockActual() : 0.0;
    }

    public ProductoInventario create(ProductoInventario p) {
        return productoRepo.save(p);
    }

    public ProductoInventario update(ProductoInventario p) {
        return productoRepo.save(p);
    }

    public void delete(Integer id) {
        productoRepo.deleteById(id);
    }

    public Integer calcularRaciones(List<IngredienteDTO> ingredientes) {
        int raciones = Integer.MAX_VALUE;
        for (IngredienteDTO ing : ingredientes) {
            ProductoInventario p = productoRepo.findById(ing.getInventarioId()).orElse(null);
            if (p == null || ing.getCantidadPorRacion() == 0) continue;
            int posibles = (int) (p.getStockActual() / ing.getCantidadPorRacion());
            raciones = Math.min(raciones, posibles);
        }
        return raciones == Integer.MAX_VALUE ? 0 : raciones;
    }

    public List<ProductoInventario> getBajoMinimo() {
        return productoRepo.findAll().stream()
                .filter(p -> p.getStockActual() != null && p.getStockMinimo() != null)
                .filter(p -> p.getStockActual() < p.getStockMinimo())
                .toList();
    }

    public ProductoInventario ajustarStock(Integer id, Double cantidad) {
        ProductoInventario producto = productoRepo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        double nuevoStock = producto.getStockActual() + cantidad;
        producto.setStockActual(Math.max(nuevoStock, 0.0));
        return productoRepo.save(producto);
    }

    public void restarIngredientesPorProducto(Integer productoId, int cantidadPlatos) {
        List<IngredienteDTO> ingredientes = ingredienteMenuClient.getIngredientesPorProducto(productoId);

        for (IngredienteDTO ing : ingredientes) {
            ProductoInventario inventario = productoRepo.findById(ing.getInventarioId())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

            double cantidadTotal = ing.getCantidadPorRacion() * cantidadPlatos;
            double nuevoStock = inventario.getStockActual() - cantidadTotal;

            inventario.setStockActual(Math.max(0.0, nuevoStock));
            productoRepo.save(inventario);
        }
    }

}
