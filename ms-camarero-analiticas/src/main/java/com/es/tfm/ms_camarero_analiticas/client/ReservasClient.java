package com.es.tfm.ms_camarero_analiticas.client;

import com.es.tfm.ms_camarero_analiticas.model.Reserva;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ReservasClient {

    private final RestTemplate restTemplate;

    @Value("${reservas.service.url}")
    private String reservasServiceUrl;

    public ReservasClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Reserva> obtenerReservasPorBar(int barId) {
        String url = reservasServiceUrl + "/bares/" + barId + "/reservas";
        Reserva[] response = restTemplate.getForObject(url, Reserva[].class);
        return Arrays.asList(response);
    }
}