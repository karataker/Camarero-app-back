package com.es.tfm.ms_camarero_notificaciones.controller;

import com.es.tfm.ms_camarero_notificaciones.model.Notificacion;
import com.es.tfm.ms_camarero_notificaciones.model.dto.NotificacionDTO;
import com.es.tfm.ms_camarero_notificaciones.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> enviar(@RequestBody NotificacionDTO dto) {
        service.guardar(dto.getTipo(), dto.getMensaje(), dto.getBarId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tipo}/bar/{barId}")
    public List<Notificacion> listarPorTipoYBar(@PathVariable String tipo, @PathVariable Integer barId) {
        return service.listarPorTipoYBar(tipo, barId);
    }

    @GetMapping("/{tipo}/bar/{barId}/no-leidas")
    public int contarNoLeidas(@PathVariable String tipo, @PathVariable Integer barId) {
        return service.contarNoLeidasPorTipoYBar(tipo, barId);
    }

    @PutMapping("/{tipo}/marcar-leidas")
    public ResponseEntity<Void> marcarComoLeidas(@PathVariable String tipo) {
        service.marcarComoLeidas(tipo);
        return ResponseEntity.ok().build();
    }


}