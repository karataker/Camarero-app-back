package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import com.es.tfm.ms_camarero_inventario.service.DetallePedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DetallePedidoControllerTest {

    private DetallePedidoService detallePedidoService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        detallePedidoService = mock(DetallePedidoService.class);
        DetallePedidoController controller = new DetallePedidoController(detallePedidoService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getByPedido_deberiaRetornarListaDetalles() throws Exception {
        when(detallePedidoService.getByPedido(5)).thenReturn(List.of(new DetallePedido(), new DetallePedido()));

        mockMvc.perform(get("/inventario/detalles/pedido/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}