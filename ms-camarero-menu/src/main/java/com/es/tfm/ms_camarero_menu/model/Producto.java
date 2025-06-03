package com.es.tfm.ms_camarero_menu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean visible;
    private String imagen;
    private Integer barId;
    private Integer inventarioIdDirecto;
    private boolean gluten;
    private boolean lacteos;
    private boolean sulfitos;
    private boolean frutosSecos;
    private boolean huevo;

    @OneToMany(mappedBy = "productoMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<IngredienteMenu> ingredientes;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public Integer getInventarioIdDirecto() {
        return inventarioIdDirecto;
    }

    public void setInventarioIdDirecto(Integer inventarioIdDirecto) {
        this.inventarioIdDirecto = inventarioIdDirecto;
    }

    public List<IngredienteMenu> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteMenu> ingredientes) {
        this.ingredientes = ingredientes;
    }
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public boolean isGluten() {
        return gluten;
    }
    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }
    public boolean isLacteos() {
        return lacteos;
    }
    public void setLacteos(boolean lacteos) {
        this.lacteos = lacteos;
    }
    public boolean isSulfitos() {
        return sulfitos;
    }
    public void setSulfitos(boolean sulfitos) {
        this.sulfitos = sulfitos;
    }
    public boolean isFrutosSecos() {
        return frutosSecos;
    }
    public void setFrutosSecos(boolean frutosSecos) {
        this.frutosSecos = frutosSecos;
    }
    public boolean isHuevo() {
        return huevo;
    }
    public void setHuevo(boolean huevo) {
        this.huevo = huevo;
    }

}