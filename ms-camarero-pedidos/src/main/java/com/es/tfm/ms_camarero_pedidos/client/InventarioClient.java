package com.es.tfm.ms_camarero_pedidos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class InventarioClient {

    private final RestTemplate restTemplate;

    @Value("${inventario.service.url}")
    private String inventarioBaseUrl;

    public InventarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void restarIngredientes(Integer productoId, int cantidad) {
        String url = UriComponentsBuilder
                .fromHttpUrl(inventarioBaseUrl + "/restar-ingredientes/" + productoId)
                .queryParam("cantidad", cantidad)
                .toUriString();

        restTemplate.postForObject(url, null, Void.class);
    }
}