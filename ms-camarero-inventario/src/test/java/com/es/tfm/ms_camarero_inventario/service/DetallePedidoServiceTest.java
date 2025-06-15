package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import com.es.tfm.ms_camarero_inventario.repository.DetallePedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoServiceTest {

    private DetallePedidoRepository detallePedidoRepository;
    private DetallePedidoService detallePedidoService;

    @BeforeEach
    void setUp() {
        detallePedidoRepository = mock(DetallePedidoRepository.class);
        detallePedidoService = new DetallePedidoService(detallePedidoRepository);
    }

    @Test
    void getByPedido_deberiaRetornarListaDetalles() {
        int pedidoId = 1;
        List<DetallePedido> detalles = List.of(new DetallePedido(), new DetallePedido());
        when(detallePedidoRepository.findByPedidoId(pedidoId)).thenReturn(detalles);

        List<DetallePedido> resultado = detallePedidoService.getByPedido(pedidoId);

        assertEquals(2, resultado.size());
        verify(detallePedidoRepository, times(1)).findByPedidoId(pedidoId);
    }
}