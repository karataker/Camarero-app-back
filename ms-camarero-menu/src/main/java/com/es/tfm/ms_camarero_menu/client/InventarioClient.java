package com.es.tfm.ms_camarero_menu.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventarioClient {

    private final RestTemplate restTemplate;

    @Value("${inventario.service.url}")
    private String inventarioBaseUrl;

    public InventarioClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public double getStockActual(Integer productoInventarioId) {
        String url = inventarioBaseUrl + "/stock/" + productoInventarioId;
        try {
            return restTemplate.getForObject(url, Double.class);
        } catch (Exception e) {
            return 0.0; // Si no encuentra o hay error, asumimos sin stock
        }
    }
}