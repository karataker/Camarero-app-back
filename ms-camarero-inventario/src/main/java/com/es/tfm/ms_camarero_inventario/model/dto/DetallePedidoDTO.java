package com.es.tfm.ms_camarero_inventario.model.dto;

public class DetallePedidoDTO {

    private Integer productoId;
    private Double cantidad;
    private Double precio;
    private String productoNombre;

    // Getters y Setters
    public DetallePedidoDTO() {
    }
    public DetallePedidoDTO(Integer productoId, Double cantidad, Double precio, String productoNombre) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.productoNombre = productoNombre;
    }
    public Integer getProductoId() {
        return productoId;
    }
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
    public Double getCantidad() {
        return cantidad;
    }
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public String getProductoNombre() {
        return productoNombre;
    }
    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }
}