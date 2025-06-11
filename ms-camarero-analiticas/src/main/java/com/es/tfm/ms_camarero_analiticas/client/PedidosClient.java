package com.es.tfm.ms_camarero_analiticas.client;

import com.es.tfm.ms_camarero_analiticas.model.Comanda;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PedidosClient {

    private final RestTemplate restTemplate;

    @Value("${pedidos.service.url}")
    private String pedidosServiceUrl;

    public PedidosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Comanda> obtenerComandasPorBar(int barId) {
        String url = pedidosServiceUrl + "/comandas/" + barId;
        Comanda[] response = restTemplate.getForObject(url, Comanda[].class);
        return Arrays.asList(response);
    }
}