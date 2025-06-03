package com.es.tfm.ms_camarero_inventario.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class ProductoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String nombre;
    private String unidad; // ej: "unidad", "gramos", "ml", "botella"
    private Double stockActual;
    private Double stockMinimo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private Integer barId;

    // Getters y setters
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
    public String getUnidad() {
        return unidad;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public Double getStockActual() {
        return stockActual;
    }
    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }
    public Double getStockMinimo() {
        return stockMinimo;
    }
    public void setStockMinimo(Double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public Integer getBarId() {
        return barId;
    }
    public void setBarId(Integer barId) {
        this.barId = barId;
    }
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
