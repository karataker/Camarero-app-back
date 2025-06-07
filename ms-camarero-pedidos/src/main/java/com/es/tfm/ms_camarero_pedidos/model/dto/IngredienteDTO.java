package com.es.tfm.ms_camarero_pedidos.model.dto;

public class IngredienteDTO {
    private Integer id;
    private String nombre;
    private Double cantidadNecesaria;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(Double cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }
}