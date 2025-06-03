package com.es.tfm.ms_camarero_menu.repository;

import com.es.tfm.ms_camarero_menu.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByBarId(Integer barId);
}