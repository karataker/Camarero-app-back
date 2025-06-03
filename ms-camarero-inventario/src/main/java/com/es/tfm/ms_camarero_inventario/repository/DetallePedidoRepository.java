package com.es.tfm.ms_camarero_inventario.repository;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findByPedidoId(Integer pedidoId);
}