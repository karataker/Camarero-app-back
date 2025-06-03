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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoProveedorService {

    private final PedidoProveedorRepository pedidoRepository;
    private final DetallePedidoRepository detalleRepository;
    private final ProductoInventarioRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public PedidoProveedorService(PedidoProveedorRepository pedidoRepository,
                                  DetallePedidoRepository detalleRepository,
                                  ProductoInventarioRepository productoRepository,
                                  ProveedorRepository proveedorRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detalleRepository = detalleRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional
    public PedidoProveedor createPedidoConDetalles(PedidoProveedor pedido, List<DetallePedidoDTO> detallesDTO) {
        PedidoProveedor nuevoPedido = pedidoRepository.save(pedido);

        for (DetallePedidoDTO dto : detallesDTO) {
            ProductoInventario producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));

            // Crear y guardar el detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(nuevoPedido);
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecio());
            detalleRepository.save(detalle);

            // Actualizar el stock actual del producto
            double nuevoStock = producto.getStockActual() + dto.getCantidad();
            producto.setStockActual(nuevoStock);
            productoRepository.save(producto); // guardar actualización
        }

        return nuevoPedido;
    }

    public List<PedidoProveedor> getByProveedor(Integer proveedorId) {
        return pedidoRepository.findByProveedorId(proveedorId);
    }

    public List<PedidoProveedor> getByBarId(Integer barId) {
        return pedidoRepository.findByBarId(barId);
    }

    public List<PedidoConDetallesDTO> getPedidosConDetallesByBar(Integer barId) {
        List<PedidoProveedor> pedidos = pedidoRepository.findByBarId(barId);

        return pedidos.stream().map(pedido -> {
            PedidoConDetallesDTO dto = new PedidoConDetallesDTO();
            dto.setId(pedido.getId());
            dto.setFecha(pedido.getFecha());
            dto.setProveedorId(pedido.getProveedor().getId());

            List<DetallePedidoDTO> detalles = detalleRepository.findByPedidoId(pedido.getId())
                    .stream()
                    .map(detalle -> {
                        DetallePedidoDTO d = new DetallePedidoDTO();
                        d.setCantidad(detalle.getCantidad());
                        d.setPrecio(detalle.getPrecioUnitario());
                        d.setProductoId(detalle.getProducto().getId());
                        d.setProductoNombre(detalle.getProducto().getNombre());
                        return d;
                    }).collect(Collectors.toList());

            dto.setDetalles(detalles);

            double total = detalles.stream()
                    .mapToDouble(d -> d.getCantidad() * d.getPrecio())
                    .sum();
            dto.setTotal(total);

            return dto;
        }).collect(Collectors.toList());
    }


}