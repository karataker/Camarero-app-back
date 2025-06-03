package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import com.es.tfm.ms_camarero_inventario.service.DetallePedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventario/detalles")
public class DetallePedidoController {

    private final DetallePedidoService service;

    public DetallePedidoController(DetallePedidoService service) {
        this.service = service;
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedido> getByPedido(@PathVariable Integer pedidoId) {
        return service.getByPedido(pedidoId);
    }
}