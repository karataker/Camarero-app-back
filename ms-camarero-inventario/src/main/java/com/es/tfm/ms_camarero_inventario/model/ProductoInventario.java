package com.es.tfm.ms_camarero_inventario.model;

import jakarta.persistence.*;


import java.math.BigDecimal;

@Entity
@Table(name = "producto_inventario")
public class ProductoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    private int stock;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    private String unidad;

    private BigDecimal precio;

    @Column(name = "bar_id")
    private Long barId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStockMinimo() {
        return stockMinimo;
    }
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    public String getUnidad() {
        return unidad;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public Long getBarId() {
        return barId;
    }
    public void setBarId(Long barId) {
        this.barId = barId;
    }

}


