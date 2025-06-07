package com.es.tfm.ms_camarero_pedidos.client;

import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.model.dto.FacturaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FacturacionClient {

    private final RestTemplate restTemplate;

    @Value("${facturacion.service.url}")
    private String facturacionUrl;

    public FacturacionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarFactura(FacturaDTO facturaDTO) {
        String url = facturacionUrl + "/facturas";
        restTemplate.postForObject(url, facturaDTO, Void.class);
    }

    public void crearFacturaDesdeComanda(ComandaDTO dto) {
        String url = facturacionUrl + "/facturas/crear-desde-comanda";
        restTemplate.postForObject(url, dto, Void.class);
    }
}