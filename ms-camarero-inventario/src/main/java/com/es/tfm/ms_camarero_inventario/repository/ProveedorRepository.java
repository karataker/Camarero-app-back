package com.es.tfm.ms_camarero_inventario.repository;

import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    List<Proveedor> findByBarId(Integer barId);
}