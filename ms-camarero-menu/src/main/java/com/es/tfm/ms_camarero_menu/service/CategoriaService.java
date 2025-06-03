package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.model.Categoria;
import com.es.tfm.ms_camarero_menu.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Obtener todas las categorías
    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    // Obtener por barId
    public List<Categoria> getByBar(Integer barId) {
        return categoriaRepository.findByBarId(barId);
    }

    // Crear nueva categoría
    public Categoria create(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Actualizar categoría existente
    public Categoria update(Integer id, Categoria categoria) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }

        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }
}