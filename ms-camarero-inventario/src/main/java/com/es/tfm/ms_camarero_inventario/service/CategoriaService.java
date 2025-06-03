package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.Categoria;
import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.repository.CategoriaRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoInventarioRepository productoInventarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoInventarioRepository productoInventarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoInventarioRepository = productoInventarioRepository;
    }

    public List<Categoria> getByBarId(Integer barId) {
        return categoriaRepository.findByBarId(barId);
    }

    public Categoria create(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<ProductoInventario> getByCategoriaNombre(String nombreCategoria) {
        Categoria categoria = categoriaRepository.findByNombre(nombreCategoria);
        if (categoria == null) return List.of();
        return productoInventarioRepository.findByCategoria(categoria);
    }
}