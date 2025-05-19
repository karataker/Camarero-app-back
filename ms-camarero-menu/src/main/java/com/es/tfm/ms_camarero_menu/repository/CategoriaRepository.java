package com.es.tfm.ms_camarero_menu.repository;

import com.es.tfm.ms_camarero_menu.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}