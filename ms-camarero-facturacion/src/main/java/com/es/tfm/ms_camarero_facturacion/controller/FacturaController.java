package com.es.tfm.ms_camarero_facturacion.controller;

import com.es.tfm.ms_camarero_facturacion.model.Factura;
import com.es.tfm.ms_camarero_facturacion.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_facturacion.service.FacturaService;
import com.es.tfm.ms_camarero_facturacion.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facturacion/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final StripeService stripeService;

    public FacturaController(FacturaService facturaService, StripeService stripeService) {
        this.facturaService = facturaService;
        this.stripeService = stripeService;
    }

    @GetMapping
    public List<Factura> obtenerFacturas() {
        return facturaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Factura obtenerPorId(@PathVariable Integer id) {
        return facturaService.obtenerPorId(id);
    }

    @PostMapping("/crear-pago")
    public Map<String, String> crearSesionPago(@RequestBody Factura factura) throws StripeException {
        String sessionId = stripeService.crearSesionPago(factura);
        return Map.of("id", sessionId);
    }

    @PostMapping("/crear-desde-comanda")
    public ResponseEntity<Void> crearFacturaDesdeComanda(@RequestBody ComandaDTO dto) {
        facturaService.crearDesdeComanda(dto);
        return ResponseEntity.ok().build();
    }

}