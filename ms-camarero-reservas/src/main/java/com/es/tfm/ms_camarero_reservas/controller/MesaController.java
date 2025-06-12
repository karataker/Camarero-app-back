package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MesaController {

    private final MesaRepository mesaRepository;
    private final BarRepository barRepository;

    public MesaController(MesaRepository mesaRepository, BarRepository barRepository) {
        this.mesaRepository = mesaRepository;
        this.barRepository = barRepository;
    }

    @GetMapping("/bares/{barId}/mesas")
    public List<Mesa> getMesasByBarId(@PathVariable Long barId) {
        return mesaRepository.findByBarId(barId);
    }


    @PostMapping("/bares/{barId}/mesas")
    public ResponseEntity<Mesa> createMesa(@PathVariable Long barId, @RequestBody Mesa mesa) {
        Optional<Bar> barOptional = barRepository.findById(barId);
        if (barOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        mesa.setBar(barOptional.get());
        Mesa savedMesa = mesaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMesa);
    }


    @PutMapping("/bares/{barId}/mesas/{codigoMesa}/ocupar")
    public ResponseEntity<?> ocuparMesa(
            @PathVariable Long barId,
            @PathVariable String codigoMesa,
            @RequestBody Map<String, Object> payload) {

        Optional<Mesa> mesaOptional = mesaRepository.findByBarIdAndCodigo(barId, codigoMesa);
        if (mesaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa con código " + codigoMesa + " no encontrada en el bar " + barId);
        }

        Mesa mesa = mesaOptional.get();

        // Validate payload for comensales
        if (!payload.containsKey("comensales")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de comensales es requerido.");
        }
        int comensales = (Integer) payload.get("comensales"); // Assuming integer from payload
        if (comensales <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de comensales debe ser mayor a cero.");
        }

        mesa.setDisponible(false);
        mesa.setEstado("ocupada");
        mesa.setComensales(comensales);
        mesa.setPedidoEnviado(false);

        mesaRepository.save(mesa);
        return ResponseEntity.ok(mesa);
    }


    // Fusionar mesas within a bar
    // Ruta: PUT /api/bares/{barId}/mesas/fusionar
    @PutMapping("/bares/{barId}/mesas/fusionar")
    public ResponseEntity<?> fusionarMesas(@PathVariable Long barId, @RequestBody Map<String, String> payload) {
        String principal = payload.get("mesaPrincipalCodigo");
        String secundaria = payload.get("mesaSecundariaCodigo");

        if (principal == null || secundaria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan parámetros: mesaPrincipalCodigo o mesaSecundariaCodigo");
        }

        List<Mesa> mesasList = mesaRepository.findByBarId(barId);
        Mesa mesaPrincipal = null;
        Mesa mesaSecundaria = null;

        for (Mesa m : mesasList) {
            if (m.getCodigo().equals(principal)) mesaPrincipal = m;
            if (m.getCodigo().equals(secundaria)) mesaSecundaria = m;
        }

        if (mesaPrincipal == null || mesaSecundaria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Una o ambas mesas no encontradas para el barId proporcionado");
        }

        mesaSecundaria.setFusionadaCon(principal);
        mesaRepository.save(mesaSecundaria);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/bares/{barId}/mesas/desfusionar/{codigo}")
    public ResponseEntity<List<Mesa>> desfusionar(@PathVariable Long barId, @PathVariable String codigo) {
        List<Mesa> mesas = mesaRepository.findByBarId(barId);

        for (Mesa mesa : mesas) {
            if (codigo.equals(mesa.getFusionadaCon())) {
                mesa.setFusionadaCon(null);
                mesaRepository.save(mesa);
            }
            if (codigo.equals(mesa.getCodigo())) {
                mesa.setDisponible(true);
                mesa.setComensales(0);
                mesa.setPedidoEnviado(false);
                mesa.setFusionadaCon(null);
                mesaRepository.save(mesa);
            }
        }

        List<Mesa> actualizadas = mesaRepository.findByBarId(barId);
        return ResponseEntity.ok(actualizadas);
    }


    @DeleteMapping("/bares/{barId}/mesas/{codigoMesa}")
    public ResponseEntity<?> eliminarMesa(
            @PathVariable Long barId,
            @PathVariable String codigoMesa) {

        Optional<Bar> barOptional = barRepository.findById(barId);
        if (barOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bar no encontrado con id: " + barId);
        }

        Mesa mesaAEliminar = mesaRepository.findByBarIdAndCodigo(barId, codigoMesa)
                .orElse(null);


        if (mesaAEliminar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa con código " + codigoMesa + " no encontrada en el bar " + barId);
        }

        boolean tieneFusionadas = mesaRepository.findByBarId(barId).stream()
                .anyMatch(m -> codigoMesa.equals(m.getFusionadaCon()));
        if (tieneFusionadas) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar: hay mesas fusionadas con esta.");
        }

        mesaRepository.delete(mesaAEliminar);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/bares/{barId}/mesas/id/{id}/liberar")
    public ResponseEntity<?> liberarMesaDirectamente(
            @PathVariable int barId,
            @PathVariable int id) {

        Mesa m = mesaRepository.findByBarIdAndId(barId, id);
        if (m == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa no encontrada");
        }

        m.setDisponible(true);
        m.setComensales(0);
        m.setPedidoEnviado(false);
        m.setFusionadaCon(null);
        m.setEstado(null);

        mesaRepository.save(m);
        return ResponseEntity.ok(m);
    }
}