package com.es.tfm.ms_camarero_inventario.model.dto;

public class IngredienteDTO {
    private Integer inventarioId;
    private Double cantidadPorRacion;

    public IngredienteDTO() {
    }
    public IngredienteDTO(Integer inventarioId, Double cantidadPorRacion) {
        this.inventarioId = inventarioId;
        this.cantidadPorRacion = cantidadPorRacion;
    }
    public Integer getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Integer inventarioId) {
        this.inventarioId = inventarioId;
    }
    public Double getCantidadPorRacion() {
        return cantidadPorRacion;
    }
    public void setCantidadPorRacion(Double cantidadPorRacion) {
        this.cantidadPorRacion = cantidadPorRacion;
    }
    @Override
    public String toString() {
        return "IngredienteDTO{" +
                "inventarioId=" + inventarioId +
                ", cantidadPorRacion=" + cantidadPorRacion +
                '}';
    }
}
