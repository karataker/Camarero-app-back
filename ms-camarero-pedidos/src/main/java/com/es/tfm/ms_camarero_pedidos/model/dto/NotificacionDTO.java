package com.es.tfm.ms_camarero_pedidos.model.dto;

public class NotificacionDTO {
    private String tipo; // "comanda", "reserva", etc.
    private String mensaje;
    private Integer barId;

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }
}

