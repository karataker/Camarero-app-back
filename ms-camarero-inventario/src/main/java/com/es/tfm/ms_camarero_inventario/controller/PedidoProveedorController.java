package com.es.tfm.ms_camarero_inventario.controller;

import com.es.tfm.ms_camarero_inventario.model.PedidoProveedor;
import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.model.dto.PedidoConDetallesDTO;
import com.es.tfm.ms_camarero_inventario.service.PedidoProveedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario/pedidos")
public class PedidoProveedorController {

    private final PedidoProveedorService service;

    public PedidoProveedorController(PedidoProveedorService service) {
        this.service = service;
    }

    @GetMapping("/bar/{barId}")
    public List<PedidoProveedor> getByBar(@PathVariable Integer barId) {
        return service.getByBarId(barId);
    }

    @GetMapping("/proveedor/{id}")
    public List<PedidoProveedor> getByProveedor(@PathVariable Integer id) {
        return service.getByProveedor(id);
    }

    @PostMapping
    public PedidoProveedor create(@RequestBody PedidoConDetallesDTO dto) {
        return service.createPedidoConDetalles(dtoToEntity(dto), dto.getDetalles());
    }

    @GetMapping("/bar/{barId}/detalles")
    public List<PedidoConDetallesDTO> getPedidosConDetallesByBar(@PathVariable Integer barId) {
        return service.getPedidosConDetallesByBar(barId);
    }

    private PedidoProveedor dtoToEntity(PedidoConDetallesDTO dto) {
        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setFecha(dto.getFecha());

        // Calcula el total desde los detalles
        double totalCalculado = dto.getDetalles().stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecio())
                .sum();
        pedido.setTotal(totalCalculado);

        Proveedor proveedor = new Proveedor();
        proveedor.setId(dto.getProveedorId());
        pedido.setProveedor(proveedor); // Solo ID es necesario para JPA merge

        pedido.setBarId(dto.getBarId());

        return pedido;
    }
}