package com.es.tfm.ms_camarero_notificaciones.repository;

import com.es.tfm.ms_camarero_notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByTipo(String tipo);
    List<Notificacion> findByTipoAndBarId(String tipo, Integer barId);
    int countByTipoAndBarIdAndLeidaFalse(String tipo, Integer barId);
}