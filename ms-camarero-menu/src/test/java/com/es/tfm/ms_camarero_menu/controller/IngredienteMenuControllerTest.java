package com.es.tfm.ms_camarero_menu.controller;

import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.service.IngredienteMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredienteMenuControllerTest {

    private IngredienteMenuService ingredienteMenuService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ingredienteMenuService = mock(IngredienteMenuService.class);
        IngredienteMenuController controller = new IngredienteMenuController(ingredienteMenuService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getByProductoMenuId_deberiaRetornarIngredientes() throws Exception {
        when(ingredienteMenuService.getByProductoMenuId(1)).thenReturn(List.of(new IngredienteMenu(), new IngredienteMenu()));

        mockMvc.perform(get("/menu/ingredientes/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void create_deberiaGuardarIngrediente() throws Exception {
        IngredienteMenu nuevo = new IngredienteMenu();
        nuevo.setCantidadPorRacion(2.0);

        when(ingredienteMenuService.create(any())).thenReturn(nuevo);

        mockMvc.perform(post("/menu/ingredientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadPorRacion").value(2.0));
    }

    @Test
    void update_deberiaActualizarIngrediente() throws Exception {
        IngredienteMenu actualizado = new IngredienteMenu();
        actualizado.setId(5);
        actualizado.setCantidadPorRacion(3.5);

        when(ingredienteMenuService.update(eq(5), any())).thenReturn(actualizado);

        mockMvc.perform(put("/menu/ingredientes/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.cantidadPorRacion").value(3.5));
    }

    @Test
    void delete_deberiaEliminarIngrediente() throws Exception {
        doNothing().when(ingredienteMenuService).delete(7);

        mockMvc.perform(delete("/menu/ingredientes/7"))
                .andExpect(status().isOk());
    }
}