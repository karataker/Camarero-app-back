package com.es.tfm.ms_camarero_analiticas.model;

import java.time.LocalDateTime;
import java.util.List;

public class Factura {
    private Integer id;
    private Integer barId;
    private String cliente;
    private Double total;
    private LocalDateTime fecha;
    private List<LineaFactura> lineas;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getBarId() { return barId; }
    public void setBarId(Integer barId) { this.barId = barId; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public List<LineaFactura> getLineas() { return lineas; }
    public void setLineas(List<LineaFactura> lineas) { this.lineas = lineas; }
}
