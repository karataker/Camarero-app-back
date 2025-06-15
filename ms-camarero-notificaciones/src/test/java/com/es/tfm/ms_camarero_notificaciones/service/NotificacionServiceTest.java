package com.es.tfm.ms_camarero_notificaciones.service;

import com.es.tfm.ms_camarero_notificaciones.model.Notificacion;
import com.es.tfm.ms_camarero_notificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionServiceTest {

    private NotificacionRepository repository;
    private NotificacionService service;

    @BeforeEach
    void setUp() {
        repository = mock(NotificacionRepository.class);
        service = new NotificacionService(repository);
    }

    @Test
    void guardar_deberiaGuardarNotificacion() {
        Notificacion n = new Notificacion();
        n.setTipo("INFO");
        n.setMensaje("Mensaje de prueba");
        n.setBarId(1);

        when(repository.save(any(Notificacion.class))).thenReturn(n);

        Notificacion resultado = service.guardar("INFO", "Mensaje de prueba", 1);

        assertEquals("INFO", resultado.getTipo());
        assertEquals("Mensaje de prueba", resultado.getMensaje());
        assertEquals(1, resultado.getBarId());
    }

    @Test
    void listarPorTipoYBar_deberiaRetornarLista() {
        List<Notificacion> lista = List.of(new Notificacion(), new Notificacion());
        when(repository.findByTipoAndBarId("ALERTA", 2)).thenReturn(lista);

        List<Notificacion> resultado = service.listarPorTipoYBar("ALERTA", 2);

        assertEquals(2, resultado.size());
    }

    @Test
    void contarNoLeidasPorTipoYBar_deberiaRetornarNumero() {
        when(repository.countByTipoAndBarIdAndLeidaFalse("URGENTE", 3)).thenReturn(5);

        int count = service.contarNoLeidasPorTipoYBar("URGENTE", 3);

        assertEquals(5, count);
    }

    @Test
    void marcarComoLeidas_deberiaActualizarNotificaciones() {
        Notificacion n1 = new Notificacion();
        n1.setLeida(false);
        Notificacion n2 = new Notificacion();
        n2.setLeida(false);

        List<Notificacion> lista = List.of(n1, n2);
        when(repository.findByTipo("INFO")).thenReturn(lista);

        service.marcarComoLeidas("INFO");

        assertTrue(n1.isLeida());
        assertTrue(n2.isLeida());
        verify(repository).saveAll(lista);
    }
}
