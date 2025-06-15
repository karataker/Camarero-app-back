package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProveedorControllerTest {

    private ProveedorService service;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        service = mock(ProveedorService.class);
        ProveedorController controller = new ProveedorController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getAll_deberiaRetornarLista() throws Exception {
        when(service.getAll()).thenReturn(List.of(new Proveedor(), new Proveedor()));

        mockMvc.perform(get("/inventario/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getByBarId_deberiaRetornarListaFiltrada() throws Exception {
        when(service.getByBarId(1)).thenReturn(List.of(new Proveedor()));

        mockMvc.perform(get("/inventario/proveedores/bar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getById_deberiaRetornarProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        when(service.getById(1)).thenReturn(proveedor);

        mockMvc.perform(get("/inventario/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_deberiaCrearProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor X");
        when(service.create(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(post("/inventario/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Proveedor X"));
    }

    @Test
    void update_deberiaActualizarProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Nuevo Nombre");
        when(service.update(eq(1), any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(put("/inventario/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Nombre"));
    }

    @Test
    void delete_deberiaEliminarProveedor() throws Exception {
        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/inventario/proveedores/1"))
                .andExpect(status().isOk());
    }
}