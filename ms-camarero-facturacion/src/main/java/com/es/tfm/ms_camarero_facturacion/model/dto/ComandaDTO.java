package com.es.tfm.ms_camarero_facturacion.model.dto;

import java.util.List;

public class ComandaDTO {

    private String cliente;
    private Integer barId;
    private String mesa; // Código de la mesa
    private List<ItemComandaDTO> items;

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

    public String getMesa() {
        return mesa;
    }
    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public List<ItemComandaDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemComandaDTO> items) {
        this.items = items;
    }
}