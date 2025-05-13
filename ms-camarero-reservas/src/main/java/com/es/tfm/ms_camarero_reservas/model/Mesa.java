package com.es.tfm.ms_camarero_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int capacidad;

    @ManyToOne
    @JoinColumn(name = "bar_id")
    private Bar bar;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
}