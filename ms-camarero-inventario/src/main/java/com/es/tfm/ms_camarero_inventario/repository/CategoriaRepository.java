package com.es.tfm.ms_camarero_inventario.repository;

import com.es.tfm.ms_camarero_inventario.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByBarId(Integer barId);
    Categoria findByNombre(String nombre);
}