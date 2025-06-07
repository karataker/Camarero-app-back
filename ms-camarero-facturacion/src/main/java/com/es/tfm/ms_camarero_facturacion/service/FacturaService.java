package com.es.tfm.ms_camarero_facturacion.service;

import com.es.tfm.ms_camarero_facturacion.model.Factura;
import com.es.tfm.ms_camarero_facturacion.model.LineaFactura;
import com.es.tfm.ms_camarero_facturacion.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_facturacion.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public void crearDesdeComanda(ComandaDTO comandaDTO) {
            if (comandaDTO.getItems() == null || comandaDTO.getItems().isEmpty()) {
                throw new IllegalArgumentException("La comanda no contiene productos");
            }

            Factura factura = new Factura();
            factura.setCliente("Cliente Noname");
            factura.setFecha(LocalDateTime.now());
            factura.setNumeroFactura("F-" + System.currentTimeMillis());
            factura.setMetodoPago("tarjeta");
            factura.setEstado("pagada");
            factura.setBarId(comandaDTO.getBarId());

            List<LineaFactura> lineas = comandaDTO.getItems().stream().map(item -> {
                LineaFactura linea = new LineaFactura();
                linea.setNombre(item.getNombre());
                linea.setCantidad(item.getCantidad());
                linea.setPrecio(item.getPrecio());
                linea.setFactura(factura);
                return linea;
            }).collect(Collectors.toList());

            factura.setLineas(lineas);

            double subtotal = lineas.stream()
                    .mapToDouble(l -> l.getPrecio() * l.getCantidad())
                    .sum();
            double iva = subtotal * 0.10;
            double total = subtotal + iva;

            factura.setSubtotal(subtotal);
            factura.setIva(iva);
            factura.setTotal(total);

            facturaRepository.save(factura);
        }

    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerPorId(int id) {
        return facturaRepository.findById(id).orElse(null);
    }
}