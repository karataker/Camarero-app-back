package com.es.tfm.ms_camarero_reservas.service;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import com.es.tfm.ms_camarero_reservas.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ReservaServiceTest {

    private ReservaRepository reservaRepository;
    private MesaRepository mesaRepository;
    private BarRepository barRepository;
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        mesaRepository = mock(MesaRepository.class);
        barRepository = mock(BarRepository.class);
        reservaService = new ReservaService(reservaRepository, mesaRepository, barRepository);
    }

    @Test
    void create_deberiaGuardarReservaConBarExistente() {
        Bar bar = new Bar();
        bar.setId(1L);
        Reserva reserva = new Reserva();
        reserva.setBar(bar);

        when(barRepository.findById(1L)).thenReturn(Optional.of(bar));
        when(reservaRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Reserva result = reservaService.create(reserva);

        assertNotNull(result);
        assertEquals("pendiente", result.getEstado());
        assertNotNull(result.getFechaSolicitud());
        verify(reservaRepository).save(any());
    }

    @Test
    void create_deberiaLanzarExcepcionSiNoHayBar() {
        Reserva reserva = new Reserva();
        reserva.setBar(new Bar()); // ID null

        assertThrows(IllegalArgumentException.class, () -> reservaService.create(reserva));
    }

    @Test
    void getAllReservas_deberiaDevolverLista() {
        when(reservaRepository.findAll()).thenReturn(List.of(new Reserva(), new Reserva()));

        List<Reserva> result = reservaService.getAllReservas();

        assertEquals(2, result.size());
        verify(reservaRepository).findAll();
    }

    @Test
    void getReservasByBarId_deberiaDevolverListaPorBar() {
        when(reservaRepository.findByBar_Id(1L)).thenReturn(List.of(new Reserva()));

        List<Reserva> result = reservaService.getReservasByBarId(1L);

        assertEquals(1, result.size());
        verify(reservaRepository).findByBar_Id(1L);
    }

    @Test
    void update_deberiaActualizarReserva() {
        Reserva original = new Reserva();
        original.setId(1L);
        original.setEstado("pendiente");

        Reserva cambios = new Reserva();
        cambios.setEstado("confirmada");
        cambios.setNombreCliente("Juan");
        cambios.setCorreoElectronico("juan@example.com");
        cambios.setTelefono("123456789");
        cambios.setNumeroComensales(4);
        cambios.setZonaPreferida("terraza");
        cambios.setFechaHora(LocalDateTime.of(2025, 7, 1, 14, 0));
        cambios.setMensaje("Cumpleaños");

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(original));
        when(reservaRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Reserva actualizada = reservaService.update(1L, cambios);

        assertEquals("confirmada", actualizada.getEstado());
        assertEquals("Juan", actualizada.getNombreCliente());
        assertEquals("juan@example.com", actualizada.getCorreoElectronico());
        assertEquals("123456789", actualizada.getTelefono());
        assertEquals(4, actualizada.getNumeroComensales());
        assertEquals("terraza", actualizada.getZonaPreferida());
        assertEquals(LocalDateTime.of(2025, 7, 1, 14, 0), actualizada.getFechaHora());
        assertEquals("Cumpleaños", actualizada.getMensaje());
        verify(reservaRepository).save(actualizada);
    }

    @Test
    void deleteReserva_deberiaEliminarReserva() {
        doNothing().when(reservaRepository).deleteById(1L);

        reservaService.deleteReserva(1L);

        verify(reservaRepository).deleteById(1L);
    }
}
