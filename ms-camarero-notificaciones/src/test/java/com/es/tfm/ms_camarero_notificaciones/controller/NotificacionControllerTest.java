package com.es.tfm.ms_camarero_notificaciones.controller;

import com.es.tfm.ms_camarero_notificaciones.model.Notificacion;
import com.es.tfm.ms_camarero_notificaciones.model.dto.NotificacionDTO;
import com.es.tfm.ms_camarero_notificaciones.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NotificacionControllerTest {

    private MockMvc mockMvc;
    private NotificacionService service;

    @BeforeEach
    void setUp() {
        service = mock(NotificacionService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new NotificacionController(service)).build();
    }

    @Test
    void enviar_deberiaGuardarYRetornarOk() throws Exception {
        NotificacionDTO dto = new NotificacionDTO("ALERTA", "Prueba", 1);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/notificaciones")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(service).guardar("ALERTA", "Prueba", 1);
    }

    @Test
    void listarPorTipoYBar_deberiaRetornarNotificaciones() throws Exception {
        when(service.listarPorTipoYBar("INFO", 2))
                .thenReturn(List.of(new Notificacion(), new Notificacion()));

        mockMvc.perform(get("/notificaciones/INFO/bar/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void contarNoLeidas_deberiaRetornarConteo() throws Exception {
        when(service.contarNoLeidasPorTipoYBar("URGENTE", 3)).thenReturn(5);

        mockMvc.perform(get("/notificaciones/URGENTE/bar/3/no-leidas"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void marcarComoLeidas_deberiaLlamarServicioYRetornarOk() throws Exception {
        mockMvc.perform(put("/notificaciones/INFO/marcar-leidas"))
                .andExpect(status().isOk());

        verify(service).marcarComoLeidas("INFO");
    }
}