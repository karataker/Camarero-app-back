package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.client.IngredienteMenuClient;
import com.es.tfm.ms_camarero_inventario.model.Categoria;
import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.model.dto.IngredienteDTO;
import com.es.tfm.ms_camarero_inventario.repository.CategoriaRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoInventarioServiceTest {

    private ProductoInventarioRepository productoRepo;
    private CategoriaRepository categoriaRepo;
    private IngredienteMenuClient ingredienteMenuClient;
    private ProductoInventarioService service;

    @BeforeEach
    void setUp() {
        productoRepo = mock(ProductoInventarioRepository.class);
        categoriaRepo = mock(CategoriaRepository.class);
        ingredienteMenuClient = mock(IngredienteMenuClient.class);

        service = new ProductoInventarioService(productoRepo, categoriaRepo, ingredienteMenuClient);
    }

    @Test
    void getStockActual_deberiaRetornarStockCorrecto() {
        ProductoInventario p = new ProductoInventario();
        p.setStockActual(20.5);
        when(productoRepo.findById(1)).thenReturn(Optional.of(p));

        Double stock = service.getStockActual(1);
        assertEquals(20.5, stock);
    }

    @Test
    void calcularRaciones_deberiaRetornarNumeroRacionesMinimo() {
        ProductoInventario prod = new ProductoInventario();
        prod.setStockActual(100.0);

        IngredienteDTO ing = new IngredienteDTO();
        ing.setInventarioId(1);
        ing.setCantidadPorRacion(20.0);

        when(productoRepo.findById(1)).thenReturn(Optional.of(prod));

        int raciones = service.calcularRaciones(List.of(ing));
        assertEquals(5, raciones);
    }

    @Test
    void getBajoMinimo_deberiaFiltrarProductosConStockBajo() {
        ProductoInventario bajo = new ProductoInventario();
        bajo.setStockActual(2.0);
        bajo.setStockMinimo(5.0);

        ProductoInventario ok = new ProductoInventario();
        ok.setStockActual(10.0);
        ok.setStockMinimo(5.0);

        when(productoRepo.findAll()).thenReturn(List.of(bajo, ok));

        List<ProductoInventario> result = service.getBajoMinimo();
        assertEquals(1, result.size());
        assertEquals(bajo, result.get(0));
    }

    @Test
    void ajustarStock_deberiaSumarCantidadCorrectamente() {
        ProductoInventario producto = new ProductoInventario();
        producto.setStockActual(10.0);

        when(productoRepo.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepo.save(any())).thenReturn(producto);

        ProductoInventario actualizado = service.ajustarStock(1, 5.0);
        assertEquals(15.0, actualizado.getStockActual());
    }

    @Test
    void getByCategoriaNombre_deberiaRetornarProductosDeCategoria() {
        Categoria cat = new Categoria();
        when(categoriaRepo.findByNombre("bebidas")).thenReturn(cat);

        when(productoRepo.findByCategoria(cat)).thenReturn(List.of(new ProductoInventario()));

        List<ProductoInventario> productos = service.getByCategoriaNombre("bebidas");
        assertEquals(1, productos.size());
    }
}
