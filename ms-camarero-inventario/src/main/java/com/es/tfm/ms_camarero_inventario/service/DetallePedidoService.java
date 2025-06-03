package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import com.es.tfm.ms_camarero_inventario.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    private final DetallePedidoRepository repo;

    public DetallePedidoService(DetallePedidoRepository repo) {
        this.repo = repo;
    }

    public List<DetallePedido> getByPedido(Integer pedidoId) {
        return repo.findByPedidoId(pedidoId);
    }
}