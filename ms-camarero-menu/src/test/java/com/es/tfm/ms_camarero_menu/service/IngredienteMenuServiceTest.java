package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.repository.IngredienteMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredienteMenuServiceTest {

    @Mock
    private IngredienteMenuRepository repo;

    @InjectMocks
    private IngredienteMenuService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByProductoMenuId_deberiaRetornarIngredientes() {
        List<IngredienteMenu> mockIngredientes = List.of(new IngredienteMenu(), new IngredienteMenu());

        when(repo.findByProductoMenuId(1)).thenReturn(mockIngredientes);

        List<IngredienteMenu> resultado = service.getByProductoMenuId(1);

        assertEquals(2, resultado.size());
        verify(repo).findByProductoMenuId(1);
    }

    @Test
    void create_deberiaGuardarIngrediente() {
        IngredienteMenu nuevo = new IngredienteMenu();
        nuevo.setCantidadPorRacion(2.5);

        when(repo.save(nuevo)).thenReturn(nuevo);

        IngredienteMenu resultado = service.create(nuevo);

        assertNotNull(resultado);
        assertEquals(2.5, resultado.getCantidadPorRacion());
        verify(repo).save(nuevo);
    }

    @Test
    void update_deberiaActualizarConId() {
        IngredienteMenu ingrediente = new IngredienteMenu();
        ingrediente.setCantidadPorRacion(1.2);

        when(repo.save(ingrediente)).thenReturn(ingrediente);

        IngredienteMenu resultado = service.update(10, ingrediente);

        assertEquals(10, resultado.getId());
        assertEquals(1.2, resultado.getCantidadPorRacion());
        verify(repo).save(ingrediente);
    }

    @Test
    void delete_deberiaLlamarADeleteById() {
        service.delete(7);
        verify(repo).deleteById(7);
    }
}
