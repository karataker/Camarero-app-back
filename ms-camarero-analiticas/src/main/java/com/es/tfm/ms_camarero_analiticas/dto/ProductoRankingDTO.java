package com.es.tfm.ms_camarero_analiticas.dto;

public class ProductoRankingDTO {
    private String nombre;
    private int cantidadVendida;
    private String categoria;

    public ProductoRankingDTO() {}
    public ProductoRankingDTO(String nombre, int cantidadVendida, String categoria) {
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(int cantidadVendida) { this.cantidadVendida = cantidadVendida; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
