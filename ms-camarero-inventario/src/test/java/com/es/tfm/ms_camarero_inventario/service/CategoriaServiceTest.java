package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.Categoria;
import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.repository.CategoriaRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaServiceTest {

    private CategoriaRepository categoriaRepository;
    private ProductoInventarioRepository productoInventarioRepository;
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaRepository = mock(CategoriaRepository.class);
        productoInventarioRepository = mock(ProductoInventarioRepository.class);
        categoriaService = new CategoriaService(categoriaRepository, productoInventarioRepository);
    }

    @Test
    void getByBarId_deberiaRetornarListaCategorias() {
        List<Categoria> categorias = List.of(new Categoria(), new Categoria());
        when(categoriaRepository.findByBarId(1)).thenReturn(categorias);

        List<Categoria> resultado = categoriaService.getByBarId(1);

        assertEquals(2, resultado.size());
        verify(categoriaRepository, times(1)).findByBarId(1);
    }

    @Test
    void create_deberiaGuardarYCategoría() {
        Categoria nueva = new Categoria();
        nueva.setNombre("Bebidas");
        when(categoriaRepository.save(nueva)).thenReturn(nueva);

        Categoria resultado = categoriaService.create(nueva);

        assertEquals("Bebidas", resultado.getNombre());
        verify(categoriaRepository).save(nueva);
    }

    @Test
    void getByCategoriaNombre_conNombreExistente_deberiaRetornarProductos() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.findByNombre("Bebidas")).thenReturn(categoria);

        List<ProductoInventario> productos = List.of(new ProductoInventario(), new ProductoInventario());
        when(productoInventarioRepository.findByCategoria(categoria)).thenReturn(productos);

        List<ProductoInventario> resultado = categoriaService.getByCategoriaNombre("Bebidas");

        assertEquals(2, resultado.size());
        verify(productoInventarioRepository).findByCategoria(categoria);
    }

    @Test
    void getByCategoriaNombre_conNombreInexistente_deberiaRetornarListaVacia() {
        when(categoriaRepository.findByNombre("Inexistente")).thenReturn(null);

        List<ProductoInventario> resultado = categoriaService.getByCategoriaNombre("Inexistente");

        assertTrue(resultado.isEmpty());
        verify(productoInventarioRepository, never()).findByCategoria(any());
    }
}