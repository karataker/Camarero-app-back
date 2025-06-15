package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.PedidoProveedor;
import com.es.tfm.ms_camarero_inventario.model.dto.DetallePedidoDTO;
import com.es.tfm.ms_camarero_inventario.model.dto.PedidoConDetallesDTO;
import com.es.tfm.ms_camarero_inventario.service.PedidoProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PedidoProveedorControllerTest {

    private PedidoProveedorService pedidoProveedorService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        pedidoProveedorService = mock(PedidoProveedorService.class);
        PedidoProveedorController controller = new PedidoProveedorController(pedidoProveedorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // ✅ Soporte para LocalDate
    }

    @Test
    void getByBar_deberiaRetornarPedidos() throws Exception {
        when(pedidoProveedorService.getByBarId(1)).thenReturn(List.of(new PedidoProveedor(), new PedidoProveedor()));

        mockMvc.perform(get("/inventario/pedidos/bar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getByProveedor_deberiaRetornarPedidos() throws Exception {
        when(pedidoProveedorService.getByProveedor(1)).thenReturn(List.of(new PedidoProveedor()));

        mockMvc.perform(get("/inventario/pedidos/proveedor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getPedidosConDetallesByBar_deberiaRetornarDTOs() throws Exception {
        DetallePedidoDTO detalle = new DetallePedidoDTO();
        detalle.setCantidad(3.0);
        detalle.setPrecio(1.5);
        detalle.setProductoId(1);

        PedidoConDetallesDTO dto = new PedidoConDetallesDTO();
        dto.setId(1);
        dto.setProveedorId(2);
        dto.setFecha(LocalDate.now());
        dto.setDetalles(List.of(detalle));

        when(pedidoProveedorService.getPedidosConDetallesByBar(1)).thenReturn(List.of(dto));

        mockMvc.perform(get("/inventario/pedidos/bar/1/detalles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void create_deberiaGuardarYRetornarPedido() throws Exception {
        DetallePedidoDTO detalle = new DetallePedidoDTO();
        detalle.setCantidad(5.0);  // ✅ Inicializado
        detalle.setPrecio(2.5);    // ✅ Inicializado
        detalle.setProductoId(1);

        PedidoConDetallesDTO dto = new PedidoConDetallesDTO();
        dto.setProveedorId(2);
        dto.setFecha(LocalDate.now());
        dto.setBarId(1);
        dto.setDetalles(List.of(detalle));

        PedidoProveedor pedidoCreado = new PedidoProveedor();
        pedidoCreado.setId(99);

        when(pedidoProveedorService.createPedidoConDetalles(any(), any())).thenReturn(pedidoCreado);

        mockMvc.perform(post("/inventario/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99));
    }
}