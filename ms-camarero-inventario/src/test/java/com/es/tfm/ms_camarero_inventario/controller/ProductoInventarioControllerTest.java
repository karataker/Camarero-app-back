package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.model.dto.IngredienteDTO;
import com.es.tfm.ms_camarero_inventario.service.ProductoInventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoInventarioControllerTest {

    private ProductoInventarioService service;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        service = mock(ProductoInventarioService.class);
        ProductoInventarioController controller = new ProductoInventarioController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getAll_deberiaRetornarProductos() throws Exception {
        when(service.getAll()).thenReturn(List.of(new ProductoInventario(), new ProductoInventario()));

        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_deberiaRetornarProducto() throws Exception {
        ProductoInventario producto = new ProductoInventario();
        producto.setId(1);
        when(service.getById(1)).thenReturn(producto);

        mockMvc.perform(get("/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByCategoria_deberiaRetornarLista() throws Exception {
        when(service.getByCategoriaNombre("bebidas")).thenReturn(List.of(new ProductoInventario()));

        mockMvc.perform(get("/inventario/categoria/bebidas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void calcularRaciones_deberiaRetornarEntero() throws Exception {
        List<IngredienteDTO> ingredientes = List.of(new IngredienteDTO());
        when(service.calcularRaciones(any())).thenReturn(5);

        mockMvc.perform(post("/inventario/cantidad-raciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientes)))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void ajustarEntrada_deberiaActualizarStock() throws Exception {
        ProductoInventario actualizado = new ProductoInventario();
        actualizado.setId(1);
        actualizado.setStockActual(10.0);
        when(service.ajustarStock(eq(1), eq(5.0))).thenReturn(actualizado);

        mockMvc.perform(post("/inventario/1/ajustar/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("cantidad", 5.0))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockActual").value(10.0));
    }

    @Test
    void ajustarSalida_deberiaActualizarStock() throws Exception {
        ProductoInventario actualizado = new ProductoInventario();
        actualizado.setId(1);
        actualizado.setStockActual(3.0);
        when(service.ajustarStock(eq(1), eq(-2.0))).thenReturn(actualizado);

        mockMvc.perform(post("/inventario/1/ajustar/salida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("cantidad", 2.0))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockActual").value(3.0));
    }

    @Test
    void restarIngredientes_deberiaRetornar200() throws Exception {
        doNothing().when(service).restarIngredientesPorProducto(1, 2);

        mockMvc.perform(post("/inventario/restar-ingredientes/1")
                        .param("cantidad", "2"))
                .andExpect(status().isOk());
    }
}