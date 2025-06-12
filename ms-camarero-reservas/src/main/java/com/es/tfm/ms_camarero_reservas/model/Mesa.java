package com.es.tfm.ms_camarero_reservas.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importación añadida
import com.fasterxml.jackson.annotation.JsonIgnore; // Esta ya estaba, pero es relevante para la relación

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo; // antes 'nombre'
    private int capacidad;
    private String zona;
    private boolean disponible;
    private boolean pedidoEnviado;
    private int comensales;
    private String fusionadaCon;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "bar_id")
    @JsonBackReference
    private Bar bar;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isPedidoEnviado() {
        return pedidoEnviado;
    }

    public void setPedidoEnviado(boolean pedidoEnviado) {
        this.pedidoEnviado = pedidoEnviado;
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public String getFusionadaCon() {
        return fusionadaCon;
    }

    public void setFusionadaCon(String fusionadaCon) {
        this.fusionadaCon = fusionadaCon;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) { // Setter añadido para el campo estado
        this.estado = estado;
    }
}