package com.es.tfm.ms_camarero_inventario.model.dto;

import java.time.LocalDate;
import java.util.List;

public class PedidoConDetallesDTO {
    private Integer id;
    private LocalDate fecha;
    private Double total;
    private Integer proveedorId;
    private List<DetallePedidoDTO> detalles;
    private Integer barId;


    // Getters y Setters

    public PedidoConDetallesDTO() {
    }
    public PedidoConDetallesDTO(Integer id, LocalDate fecha, Double total, Integer proveedorId, List<DetallePedidoDTO> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.proveedorId = proveedorId;
        this.detalles = detalles;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }

    public Integer getBarId() { return barId; }
    public void setBarId(Integer barId) { this.barId = barId; }
}

