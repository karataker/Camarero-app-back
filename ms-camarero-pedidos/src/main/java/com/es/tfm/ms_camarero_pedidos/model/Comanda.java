package com.es.tfm.ms_camarero_pedidos.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer barId;

    private String mesaCodigo;

    private LocalDateTime fecha = LocalDateTime.now();

    private String estado = "Pendiente";

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  //cambio JM para evitar el bucle infinito de serialización
    private List<ItemComanda> items;

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBarId() {
        return barId;
    }
    public void setBarId(Integer barId) {
        this.barId = barId;
    }
    public String getMesaCodigo() {
        return mesaCodigo;
    }
    public void setMesaCodigo(String mesaCodigo) {
        this.mesaCodigo = mesaCodigo;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public List<ItemComanda> getItems() {
        return items;
    }
    public void setItems(List<ItemComanda> items) {
        this.items = items;
    }
}