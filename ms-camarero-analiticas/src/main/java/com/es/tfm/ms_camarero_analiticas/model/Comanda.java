package com.es.tfm.ms_camarero_analiticas.model;

import java.time.LocalDateTime;
import java.util.List;

public class Comanda {
    private Integer id;
    private Integer barId;
    private String mesaCodigo;
    private LocalDateTime fecha;
    private String estado;
    private List<ItemComanda> items;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getBarId() { return barId; }
    public void setBarId(Integer barId) { this.barId = barId; }
    public String getMesaCodigo() { return mesaCodigo; }
    public void setMesaCodigo(String mesaCodigo) { this.mesaCodigo = mesaCodigo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<ItemComanda> getItems() { return items; }
    public void setItems(List<ItemComanda> items) { this.items = items; }
}
