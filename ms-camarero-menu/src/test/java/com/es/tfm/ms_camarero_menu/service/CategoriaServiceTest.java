package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.model.Categoria;
import com.es.tfm.ms_camarero_menu.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_deberiaRetornarListaCategorias() {
        List<Categoria> mockCategorias = List.of(new Categoria(), new Categoria());

        when(categoriaRepository.findAll()).thenReturn(mockCategorias);

        List<Categoria> resultado = categoriaService.getAll();

        assertEquals(2, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void getByBar_deberiaRetornarCategoriasPorBar() {
        List<Categoria> mockCategorias = List.of(new Categoria());

        when(categoriaRepository.findByBarId(1)).thenReturn(mockCategorias);

        List<Categoria> resultado = categoriaService.getByBar(1);

        assertEquals(1, resultado.size());
        verify(categoriaRepository).findByBarId(1);
    }

    @Test
    void create_deberiaGuardarCategoria() {
        Categoria nueva = new Categoria();
        nueva.setNombre("Bebidas");

        when(categoriaRepository.save(nueva)).thenReturn(nueva);

        Categoria resultado = categoriaService.create(nueva);

        assertEquals("Bebidas", resultado.getNombre());
        verify(categoriaRepository).save(nueva);
    }

    @Test
    void update_categoriaExistente_deberiaActualizar() {
        Categoria existente = new Categoria();
        existente.setId(5);
        existente.setNombre("Platos");

        Categoria actualizado = new Categoria();
        actualizado.setNombre("Postres");

        when(categoriaRepository.findById(5)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any())).thenAnswer(invoc -> invoc.getArgument(0)); // Devuelve lo que se guarda

        Categoria resultado = categoriaService.update(5, actualizado);

        assertEquals(5, resultado.getId());
        assertEquals("Postres", resultado.getNombre());
    }

    @Test
    void update_categoriaInexistente_deberiaLanzarExcepcion() {
        Categoria actualizado = new Categoria();
        when(categoriaRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoriaService.update(999, actualizado);
        });

        assertTrue(exception.getMessage().contains("Categoría no encontrada"));
    }
}
