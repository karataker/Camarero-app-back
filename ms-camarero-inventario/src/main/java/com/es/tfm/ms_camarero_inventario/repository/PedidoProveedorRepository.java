package com.es.tfm.ms_camarero_inventario.repository;

import com.es.tfm.ms_camarero_inventario.model.PedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoProveedorRepository extends JpaRepository<PedidoProveedor, Integer> {
    List<PedidoProveedor> findByProveedorId(Integer proveedorId);
    List<PedidoProveedor> findByBarId(Integer barId);
}
