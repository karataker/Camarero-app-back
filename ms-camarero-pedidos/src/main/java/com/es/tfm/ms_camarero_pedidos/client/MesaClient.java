package com.es.tfm.ms_camarero_pedidos.client;

import com.es.tfm.ms_camarero_pedidos.model.dto.MesaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MesaClient {

    private final RestTemplate restTemplate;

    @Value("${mesas.service.url}")
    private String mesasBaseUrl;

    public MesaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String obtenerCodigoMesa(Integer barId, Integer mesaId) {
        String url = UriComponentsBuilder
                .fromHttpUrl(mesasBaseUrl + "/bares/" + barId + "/mesas")
                .toUriString();

        try {
            MesaDTO[] mesas = restTemplate.getForObject(url, MesaDTO[].class);
            if (mesas != null) {
                for (MesaDTO mesa : mesas) {
                    if (mesa.getId().equals(mesaId)) {
                        return mesa.getCodigo(); // o getNombre() si prefieres
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el código de la mesa: " + e.getMessage());
        }

        return "Mesa " + mesaId;
    }
}
