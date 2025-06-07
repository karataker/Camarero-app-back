package com.es.tfm.ms_camarero_pedidos.client;


import com.es.tfm.ms_camarero_pedidos.model.dto.NotificacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificacionClient {

    private final RestTemplate restTemplate;

    @Value("${notificaciones.service.url}")
    private String notificacionesUrl;

    public NotificacionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarNotificacion(String tipo, String mensaje, Integer barId) {
        String url = notificacionesUrl;

        Map<String, Object> payload = new HashMap<>();
        payload.put("tipo", tipo);
        payload.put("mensaje", mensaje);
        payload.put("barId", barId);

        restTemplate.postForObject(url, payload, Void.class);
    }
}

