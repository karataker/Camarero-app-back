package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.Categoria;
import com.es.tfm.ms_camarero_menu.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoriaControllerTest {

    private CategoriaService categoriaService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categoriaService = mock(CategoriaService.class);
        CategoriaController controller = new CategoriaController(categoriaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAll_deberiaRetornarListaCategorias() throws Exception {
        when(categoriaService.getAll()).thenReturn(List.of(new Categoria(), new Categoria()));

        mockMvc.perform(get("/menu/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}