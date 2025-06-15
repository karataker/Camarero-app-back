package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservaControllerTest {

    private ReservaService reservaService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reservaService = mock(ReservaService.class);
        ReservaController controller = new ReservaController(reservaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    void createReserva_deberiaRetornar201() throws Exception {
        Reserva nueva = new Reserva();
        nueva.setNombreCliente("Juan");
        nueva.setFechaSolicitud(LocalDateTime.now());

        when(reservaService.create(any())).thenReturn(nueva);

        mockMvc.perform(post("/api/bares/1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCliente").value("Juan"));
    }

    @Test
    void getAllReservas_deberiaRetornarLista() throws Exception {
        when(reservaService.getAllReservas()).thenReturn(List.of(new Reserva(), new Reserva()));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getReservasByBarId_deberiaRetornarFiltrado() throws Exception {
        when(reservaService.getReservasByBarId(1L)).thenReturn(List.of(new Reserva()));

        mockMvc.perform(get("/api/bares/1/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void updateReserva_deberiaActualizarCorrectamente() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Ana");

        when(reservaService.update(eq(1L), any())).thenReturn(reserva);

        mockMvc.perform(put("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCliente").value("Ana"));
    }

    @Test
    void deleteReserva_deberiaRetornar204() throws Exception {
        doNothing().when(reservaService).deleteReserva(1L);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk());
    }
}