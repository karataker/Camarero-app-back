package com.es.tfm.ms_camarero_notificaciones.model.dto;

public class NotificacionDTO {
    private String tipo;
    private String mensaje;
    private Integer barId;

    // Constructor
    public NotificacionDTO() {}
    public NotificacionDTO(String tipo, String mensaje, Integer barId) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.barId = barId;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Integer getBarId() { return barId; }
    public void setBarId(Integer barId) { this.barId = barId; }
}