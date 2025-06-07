package com.es.tfm.ms_camarero_pedidos.controller;

import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.service.ComandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}