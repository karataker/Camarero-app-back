package com.es.tfm.ms_camarero_inventario.client;


import com.es.tfm.ms_camarero_inventario.model.dto.IngredienteDTO;
import com.es.tfm.ms_camarero_inventario.model.response.IngredienteMenuResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredienteMenuClient {

    private final RestTemplate restTemplate;

    @Value("${menu.service.url}")
    private String menuServiceUrl;

    public IngredienteMenuClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<IngredienteDTO> getIngredientesPorProducto(Integer productoId) {
        String url = menuServiceUrl + "/ingredientes/producto/" + productoId;
        IngredienteMenuResponse[] ingredientes = restTemplate.getForObject(url, IngredienteMenuResponse[].class);

        return Arrays.stream(ingredientes)
                .map(ing -> {
                    IngredienteDTO dto = new IngredienteDTO();
                    dto.setInventarioId(ing.getProductoInventarioId());
                    dto.setCantidadPorRacion(ing.getCantidadPorRacion());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
