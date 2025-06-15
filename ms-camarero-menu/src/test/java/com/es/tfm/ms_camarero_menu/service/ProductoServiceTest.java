package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.client.InventarioClient;
import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.model.Producto;
import com.es.tfm.ms_camarero_menu.repository.IngredienteMenuRepository;
import com.es.tfm.ms_camarero_menu.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private IngredienteMenuRepository ingredienteMenuRepository;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_deberiaRetornarTodosLosProductos() {
        List<Producto> mockProductos = List.of(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(mockProductos);

        List<Producto> resultado = productoService.getAll();

        assertEquals(2, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void getById_existente_deberiaRetornarProducto() {
        Producto producto = new Producto();
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.getById(1);

        assertNotNull(resultado);
        verify(productoRepository).findById(1);
    }

    @Test
    void getById_inexistente_deberiaRetornarNull() {
        when(productoRepository.findById(999)).thenReturn(Optional.empty());

        Producto resultado = productoService.getById(999);

        assertNull(resultado);
    }

    @Test
    void getByBar_deberiaRetornarProductosDelBar() {
        List<Producto> mockProductos = List.of(new Producto());
        when(productoRepository.findByBarId(1)).thenReturn(mockProductos);

        List<Producto> resultado = productoService.getByBar(1);

        assertEquals(1, resultado.size());
    }

    @Test
    void save_productoConIngredientes_deberiaAsignarRelacionYGuardar() {
        Producto producto = new Producto();
        IngredienteMenu ing = new IngredienteMenu();
        producto.setIngredientes(List.of(ing));

        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto resultado = productoService.save(producto);

        assertNotNull(resultado);
        assertEquals(producto, ing.getProductoMenu());
        verify(productoRepository).save(producto);
    }

    @Test
    void delete_deberiaEliminarPorId() {
        productoService.delete(5);
        verify(productoRepository).deleteById(5);
    }

    @Test
    void calcularRacionesDisponibles_conInventarioDirecto() {
        Producto producto = new Producto();
        producto.setInventarioIdDirecto(123);

        when(inventarioClient.getStockActual(123)).thenReturn(10.0);

        int raciones = productoService.calcularRacionesDisponibles(producto);

        assertEquals(10, raciones);
    }

    @Test
    void calcularRacionesDisponibles_conIngredientes() {
        IngredienteMenu ing1 = new IngredienteMenu();
        ing1.setProductoInventarioId(1);
        ing1.setCantidadPorRacion(2.0);

        IngredienteMenu ing2 = new IngredienteMenu();
        ing2.setProductoInventarioId(2);
        ing2.setCantidadPorRacion(1.0);

        Producto producto = new Producto();
        producto.setIngredientes(Arrays.asList(ing1, ing2));

        when(inventarioClient.getStockActual(1)).thenReturn(10.0);
        when(inventarioClient.getStockActual(2)).thenReturn(5.0);

        int raciones = productoService.calcularRacionesDisponibles(producto);

        // raciones posibles: 10/2 = 5, 5/1 = 5 ⇒ mínimo = 5
        assertEquals(5, raciones);
    }

    @Test
    void calcularRacionesDisponibles_sinIngredientes() {
        Producto producto = new Producto();
        producto.setIngredientes(Collections.emptyList());

        int raciones = productoService.calcularRacionesDisponibles(producto);

        assertEquals(0, raciones);
    }
}
