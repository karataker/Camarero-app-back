package com.es.tfm.ms_camarero_analiticas.client;

import com.es.tfm.ms_camarero_analiticas.model.Factura;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FacturacionClient {

    private final RestTemplate restTemplate;

    @Value("${facturacion.service.url}")
    private String facturacionServiceUrl;

    public FacturacionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Factura> getFacturasPorBarYAnio(int barId, int anio) {
        String url = facturacionServiceUrl + "/facturas/bares/" + barId;
        Factura[] response = restTemplate.getForObject(url, Factura[].class);
        return Arrays.asList(response);
    }
}
