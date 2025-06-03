package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.repository.IngredienteMenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteMenuService {

    private final IngredienteMenuRepository repo;

    public IngredienteMenuService(IngredienteMenuRepository repo) {
        this.repo = repo;
    }

    public List<IngredienteMenu> getByProductoMenuId(Integer productoId) {
        return repo.findByProductoMenuId(productoId);
    }

    public IngredienteMenu create(IngredienteMenu ingrediente) {
        return repo.save(ingrediente);
    }

    public IngredienteMenu update(Integer id, IngredienteMenu ingrediente) {
        ingrediente.setId(id);
        return repo.save(ingrediente);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}