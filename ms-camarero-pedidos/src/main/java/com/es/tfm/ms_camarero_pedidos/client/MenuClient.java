package com.es.tfm.ms_camarero_pedidos.client;

import com.es.tfm.ms_camarero_pedidos.model.dto.IngredienteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class MenuClient {

    private final RestTemplate restTemplate;

    @Value("${menu.service.url}")
    private String menuUrl;

    public MenuClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<IngredienteDTO> obtenerIngredientesProducto(Integer productoId) {
        String url = menuUrl + "/ingredientes/producto/" + productoId;
        IngredienteDTO[] response = restTemplate.getForObject(url, IngredienteDTO[].class);
        return Arrays.asList(response);
    }
}
