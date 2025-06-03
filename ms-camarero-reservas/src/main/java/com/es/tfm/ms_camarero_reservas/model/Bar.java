package com.es.tfm.ms_camarero_reservas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference; // Importación añadida
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) // fetch = FetchType.EAGER añadido
    @JsonManagedReference // Anotación añadida
    private List<Mesa> mesas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }
}