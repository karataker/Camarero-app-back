package com.es.tfm.ms_camarero_analiticas.model;

public class LineaFactura {
    private Integer id;
    private String nombreProducto;
    private Double precioUnitario;
    private Integer cantidad;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
