package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorServiceTest {

    private ProveedorRepository proveedorRepository;
    private ProveedorService service;

    @BeforeEach
    void setUp() {
        proveedorRepository = mock(ProveedorRepository.class);
        service = new ProveedorService(proveedorRepository);
    }

    @Test
    void getAll_deberiaRetornarListaProveedores() {
        when(proveedorRepository.findAll()).thenReturn(List.of(new Proveedor(), new Proveedor()));

        List<Proveedor> result = service.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void getById_deberiaRetornarProveedorCorrecto() {
        Proveedor p = new Proveedor();
        p.setId(1);

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(p));

        Proveedor result = service.getById(1);
        assertEquals(1, result.getId());
    }

    @Test
    void create_deberiaGuardarProveedor() {
        Proveedor p = new Proveedor();
        when(proveedorRepository.save(p)).thenReturn(p);

        Proveedor result = service.create(p);
        assertEquals(p, result);
    }

    @Test
    void update_deberiaActualizarProveedorExistente() {
        Proveedor existente = new Proveedor();
        existente.setId(1);
        existente.setNombre("Antiguo");

        Proveedor cambios = new Proveedor();
        cambios.setNombre("Nuevo");
        cambios.setTelefono("123");
        cambios.setEmail("nuevo@mail.com");

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(existente));
        when(proveedorRepository.save(any())).thenReturn(existente);

        Proveedor actualizado = service.update(1, cambios);

        assertEquals("Nuevo", actualizado.getNombre());
        assertEquals("123", actualizado.getTelefono());
        assertEquals("nuevo@mail.com", actualizado.getEmail());
    }

    @Test
    void delete_deberiaEliminarProveedor() {
        service.delete(1);
        verify(proveedorRepository).deleteById(1);
    }

    @Test
    void getByBarId_deberiaRetornarListaFiltrada() {
        when(proveedorRepository.findByBarId(5)).thenReturn(List.of(new Proveedor()));

        List<Proveedor> result = service.getByBarId(5);
        assertEquals(1, result.size());
    }
}