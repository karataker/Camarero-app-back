package com.es.tfm.ms_camarero_pedidos.model.dto;

public class ItemDTO {
    private String nombre;
    private int cantidad;
    private Integer productoId;
    private double precio;

    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public Integer getProductoId() { return productoId; }
    public double getPrecio() { return precio; }
}