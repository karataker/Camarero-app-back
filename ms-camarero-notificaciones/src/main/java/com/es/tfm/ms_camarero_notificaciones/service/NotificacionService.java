package com.es.tfm.ms_camarero_notificaciones.service;

import com.es.tfm.ms_camarero_notificaciones.model.Notificacion;
import com.es.tfm.ms_camarero_notificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public Notificacion guardar(String tipo, String mensaje,Integer barId) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(tipo);
        notificacion.setMensaje(mensaje);
        notificacion.setBarId(barId);
        return repository.save(notificacion);
    }

    public List<Notificacion> listarPorTipoYBar(String tipo, Integer barId) {
        return repository.findByTipoAndBarId(tipo, barId);
    }

    public int contarNoLeidasPorTipoYBar(String tipo, Integer barId) {
        return repository.countByTipoAndBarIdAndLeidaFalse(tipo, barId);
    }

    public void marcarComoLeidas(String tipo) {
        List<Notificacion> pendientes = repository.findByTipo(tipo);
        pendientes.forEach(n -> n.setLeida(true));
        repository.saveAll(pendientes);
    }
}
