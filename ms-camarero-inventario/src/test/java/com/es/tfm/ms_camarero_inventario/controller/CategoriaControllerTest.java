package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.Categoria;
import com.es.tfm.ms_camarero_inventario.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class CategoriaControllerTest {

    private MockMvc mockMvc;
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaService = mock(CategoriaService.class);
        CategoriaController controller = new CategoriaController(categoriaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getByBar_deberiaRetornarListaCategorias() throws Exception {
        when(categoriaService.getByBarId(1)).thenReturn(List.of(new Categoria(), new Categoria()));

        mockMvc.perform(get("/inventario/categorias/bar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void create_deberiaRetornarCategoriaCreada() throws Exception {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre("Bebidas");

        when(categoriaService.create(any(Categoria.class))).thenReturn(nuevaCategoria);

        mockMvc.perform(post("/inventario/categorias")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(nuevaCategoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Bebidas"));
    }
}
