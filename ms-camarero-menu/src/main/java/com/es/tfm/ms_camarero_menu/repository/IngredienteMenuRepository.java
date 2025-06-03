package com.es.tfm.ms_camarero_menu.repository;


import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngredienteMenuRepository extends JpaRepository<IngredienteMenu, Integer> {
    List<IngredienteMenu> findByProductoMenuId(Integer productoMenuId);
}