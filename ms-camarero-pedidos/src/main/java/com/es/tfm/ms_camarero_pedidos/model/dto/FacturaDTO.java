package com.es.tfm.ms_camarero_pedidos.model.dto;

import java.util.List;

public class FacturaDTO {
    private String cliente;
    private Integer barId;
    private List<LineaFacturaDTO> lineas;

    // Getters y Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public List<LineaFacturaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaFacturaDTO> lineas) {
        this.lineas = lineas;
    }
}
