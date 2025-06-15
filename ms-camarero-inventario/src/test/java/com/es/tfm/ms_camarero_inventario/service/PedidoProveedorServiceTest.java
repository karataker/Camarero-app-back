package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.DetallePedido;
import com.es.tfm.ms_camarero_inventario.model.PedidoProveedor;
import com.es.tfm.ms_camarero_inventario.model.ProductoInventario;
import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.model.dto.DetallePedidoDTO;
import com.es.tfm.ms_camarero_inventario.model.dto.PedidoConDetallesDTO;
import com.es.tfm.ms_camarero_inventario.repository.DetallePedidoRepository;
import com.es.tfm.ms_camarero_inventario.repository.PedidoProveedorRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProductoInventarioRepository;
import com.es.tfm.ms_camarero_inventario.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PedidoProveedorServiceTest {

    private PedidoProveedorRepository pedidoRepo;
    private DetallePedidoRepository detalleRepo;
    private ProductoInventarioRepository productoRepo;
    private ProveedorRepository proveedorRepo;
    private PedidoProveedorService service;

    @BeforeEach
    void setUp() {
        pedidoRepo = mock(PedidoProveedorRepository.class);
        detalleRepo = mock(DetallePedidoRepository.class);
        productoRepo = mock(ProductoInventarioRepository.class);
        proveedorRepo = mock(ProveedorRepository.class);

        service = new PedidoProveedorService(pedidoRepo, detalleRepo, productoRepo, proveedorRepo);
    }

    @Test
    void createPedidoConDetalles_deberiaGuardarPedidoYActualizarStock() {
        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setId(1);
        pedido.setFecha(LocalDate.now());
        pedido.setBarId(2);

        DetallePedidoDTO detalleDTO = new DetallePedidoDTO();
        detalleDTO.setCantidad(5.0);
        detalleDTO.setPrecio(2.0);
        detalleDTO.setProductoId(10);

        ProductoInventario producto = new ProductoInventario();
        producto.setId(10);
        producto.setStockActual(20.0);

        when(pedidoRepo.save(any())).thenReturn(pedido);
        when(productoRepo.findById(10)).thenReturn(Optional.of(producto));

        PedidoProveedor result = service.createPedidoConDetalles(pedido, List.of(detalleDTO));

        assertNotNull(result);
        verify(pedidoRepo).save(any());
        verify(productoRepo).save(any());
        verify(detalleRepo).save(any());
    }

    @Test
    void getByProveedor_deberiaRetornarLista() {
        when(pedidoRepo.findByProveedorId(1)).thenReturn(List.of(new PedidoProveedor()));
        List<PedidoProveedor> pedidos = service.getByProveedor(1);
        assertEquals(1, pedidos.size());
    }

    @Test
    void getByBarId_deberiaRetornarLista() {
        when(pedidoRepo.findByBarId(1)).thenReturn(List.of(new PedidoProveedor()));
        List<PedidoProveedor> pedidos = service.getByBarId(1);
        assertEquals(1, pedidos.size());
    }
}