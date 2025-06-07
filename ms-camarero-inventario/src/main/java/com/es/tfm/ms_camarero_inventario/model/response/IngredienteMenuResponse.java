package com.es.tfm.ms_camarero_inventario.model.response;

public class IngredienteMenuResponse {
    private Integer productoInventarioId;
    private double cantidadPorRacion;

    public Integer getProductoInventarioId() {
        return productoInventarioId;
    }

    public void setProductoInventarioId(Integer productoInventarioId) {
        this.productoInventarioId = productoInventarioId;
    }

    public double getCantidadPorRacion() {
        return cantidadPorRacion;
    }

    public void setCantidadPorRacion(double cantidadPorRacion) {
        this.cantidadPorRacion = cantidadPorRacion;
    }
}