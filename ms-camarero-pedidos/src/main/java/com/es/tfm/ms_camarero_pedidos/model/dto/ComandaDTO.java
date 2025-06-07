package com.es.tfm.ms_camarero_pedidos.model.dto;

import java.util.List;

public class ComandaDTO {
    private String cliente;
    private Integer barId;
    private String mesaCodigo;
    private List<ItemDTO> items;

    public String getCliente() { return cliente; }
    public Integer getBarId() { return barId; }
    public String getMesaCodigo() { return mesaCodigo; }
    public List<ItemDTO> getItems() { return items; }
}
