package com.es.tfm.ms_camarero_inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PedidoProveedor pedido;
    @ManyToOne
    private ProductoInventario producto;
    private Double cantidad;
    private Double precioUnitario;

    // Getters y setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public PedidoProveedor getPedido() {
        return pedido;
    }
    public void setPedido(PedidoProveedor pedido) {
        this.pedido = pedido;
    }
    public ProductoInventario getProducto() {
        return producto;
    }
    public void setProducto(ProductoInventario producto) {
        this.producto = producto;
    }
    public Double getCantidad() {
        return cantidad;
    }
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
    public Double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}

