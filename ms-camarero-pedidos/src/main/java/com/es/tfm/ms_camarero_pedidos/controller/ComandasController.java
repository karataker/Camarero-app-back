package com.es.tfm.ms_camarero_pedidos.controller;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.service.ComandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/comandas")
public class ComandasController {

    private final ComandaService comandaService;

    public ComandasController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmarComanda(@RequestBody ComandaDTO comandaDTO) {
        try {
            comandaService.procesarComanda(comandaDTO);
            return ResponseEntity.ok("Comanda procesada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la comanda: " + e.getMessage());
        }
    }

    // JM añadido para funcionamiento de llamada getComandasPorMesa
    @GetMapping("/{barId}/{mesaCodigo}")
    public ResponseEntity<List<Comanda>> getComandasPorMesa(
            @PathVariable("barId") Long barId,
            @PathVariable("mesaCodigo") String mesaCodigo) {

        List<Comanda> comandas = comandaService.getComandasPorMesa(barId, mesaCodigo);
        if (comandas.isEmpty()) {
            return ResponseEntity.noContent().build(); // O ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comandas);
    }

}