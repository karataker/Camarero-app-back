package com.es.tfm.ms_camarero_facturacion.service;

import com.es.tfm.ms_camarero_facturacion.model.Factura;
import com.es.tfm.ms_camarero_facturacion.model.LineaFactura;
import com.es.tfm.ms_camarero_facturacion.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura crearFactura(Factura factura) {
        // Establecer fecha actual si no se proporcionó
        if (factura.getFecha() == null) {
            factura.setFecha(LocalDateTime.now());
        }

        // Relacionar líneas con factura y calcular totales
        double subtotal = 0;
        for (LineaFactura linea : factura.getLineas()) {
            linea.setFactura(factura);
            subtotal += linea.getCantidad() * linea.getPrecio();
        }

        double iva = subtotal * 0.21;
        double total = subtotal + iva;

        factura.setSubtotal(subtotal);
        factura.setIva(iva);
        factura.setTotal(total);

        return facturaRepository.save(factura);
    }

    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerPorId(int id) {
        return facturaRepository.findById(id).orElse(null);
    }
}