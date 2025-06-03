package com.es.tfm.ms_camarero_menu.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingrediente_menu")
public class IngredienteMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productoInventarioId;

    private Double cantidadPorRacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_menu_id")
    @JsonBackReference
    private Producto productoMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoInventarioId() {
        return productoInventarioId;
    }

    public void setProductoInventarioId(Integer productoInventarioId) {
        this.productoInventarioId = productoInventarioId;
    }

    public Double getCantidadPorRacion() {
        return cantidadPorRacion;
    }

    public void setCantidadPorRacion(Double cantidadPorRacion) {
        this.cantidadPorRacion = cantidadPorRacion;
    }

    public Producto getProductoMenu() {
        return productoMenu;
    }

    public void setProductoMenu(Producto productoMenu) {
        this.productoMenu = productoMenu;
    }
}
