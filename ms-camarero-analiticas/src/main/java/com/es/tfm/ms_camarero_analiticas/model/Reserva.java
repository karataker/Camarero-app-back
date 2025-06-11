package com.es.tfm.ms_camarero_analiticas.model;

import java.time.LocalDateTime;

public class Reserva {
    private Long id;
    private String nombreCliente;
    private int numeroComensales;
    private LocalDateTime fechaHora;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public int getNumeroComensales() { return numeroComensales; }
    public void setNumeroComensales(int numeroComensales) { this.numeroComensales = numeroComensales; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
