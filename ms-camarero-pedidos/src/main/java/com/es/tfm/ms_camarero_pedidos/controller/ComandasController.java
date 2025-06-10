package com.es.tfm.ms_camarero_pedidos.controller;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.service.ComandaService;
import com.es.tfm.ms_camarero_pedidos.model.ItemComanda;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/comandas")
public class ComandasController {

    private final ComandaService comandaService;

    @Autowired
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
            @PathVariable("barId") Integer barId,
            @PathVariable("mesaCodigo") String mesaCodigo) {

        List<Comanda> comandas = comandaService.getComandasPorMesa(barId, mesaCodigo);
        if (comandas.isEmpty()) {
            return ResponseEntity.noContent().build(); // O ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comandas);
    }

    // JM añadido para funcionamiento de llamada getComandasPorMesa

    @GetMapping("/{barId}")
    public ResponseEntity<List<Comanda>> getComandasPorBar(@PathVariable("barId") Integer barId) {
        List<Comanda> comandas = comandaService.getComandasPorBar(barId);
        if (comandas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comandas);
    }


    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ItemComanda> actualizarEstadoItem(
            @PathVariable("itemId") Integer itemId,
            @RequestParam("nuevoEstado") String nuevoEstado) {
        try {
            ItemComanda itemActualizado = comandaService.actualizarEstadoItem(itemId, nuevoEstado);
            return ResponseEntity.ok(itemActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{barId}/{mesaCodigo}/terminar")
    public ResponseEntity<?> marcarComandasComoTerminadas(
            @PathVariable("barId") Integer barId,
            @PathVariable("mesaCodigo") String mesaCodigo) {

        try {
            comandaService.marcarComandasComoTerminadas(barId, mesaCodigo);
            return ResponseEntity.ok("Comandas marcadas como terminadas correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al marcar comandas como terminadas: " + e.getMessage());
        }
    }

}