package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.Producto;
import com.es.tfm.ms_camarero_menu.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_debeRetornarListaDeProductos() throws Exception {
        when(productoService.getAll()).thenReturn(List.of(new Producto(), new Producto()));

        mockMvc.perform(get("/menu/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_existente_debeRetornarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1);
        when(productoService.getById(1)).thenReturn(producto);

        mockMvc.perform(get("/menu/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_inexistente_debeRetornarNotFound() throws Exception {
        when(productoService.getById(999)).thenReturn(null);

        mockMvc.perform(get("/menu/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_debeGuardarYRetornarProducto() throws Exception {
        Producto nuevo = new Producto();
        nuevo.setNombre("Pizza");
        Producto guardado = new Producto();
        guardado.setId(1);
        guardado.setNombre("Pizza");

        when(productoService.save(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/menu/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void calcularRaciones_disponibles_debeRetornarEntero() throws Exception {
        Producto producto = new Producto();
        when(productoService.getById(1)).thenReturn(producto);
        when(productoService.calcularRacionesDisponibles(producto)).thenReturn(7);

        mockMvc.perform(get("/menu/productos/1/raciones-disponibles"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

    @Test
    void calcularRaciones_productoNoExiste_debeRetornarNotFound() throws Exception {
        when(productoService.getById(1)).thenReturn(null);

        mockMvc.perform(get("/menu/productos/1/raciones-disponibles"))
                .andExpect(status().isNotFound());
    }
}