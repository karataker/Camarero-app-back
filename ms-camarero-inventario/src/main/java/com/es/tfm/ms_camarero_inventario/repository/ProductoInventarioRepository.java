package com.es.tfm.ms_camarero_inventario.repository;

import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoInventarioRepository extends JpaRepository<ProductoInventario, Long> {
    List<ProductoInventario> findByBarId(Long barId);
}