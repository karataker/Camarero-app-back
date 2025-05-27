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
@RequestMapping("/api/bares/{barId}/mesas")
public class MesaController {

    private final MesaRepository mesaRepository;
    private final BarRepository barRepository;

    public MesaController(MesaRepository mesaRepository, BarRepository barRepository) {
        this.mesaRepository = mesaRepository;
        this.barRepository = barRepository;
    }

    @GetMapping
    public List<Mesa> getMesas(@PathVariable int barId) {
        return mesaRepository.findByBarId(barId);
    }

    @PostMapping
    public Mesa createMesa(@PathVariable Long barId, @RequestBody Mesa mesa) {
        Bar bar = barRepository.findById(barId).orElseThrow();
        mesa.setBar(bar);
        return mesaRepository.save(mesa);
    }

    @PutMapping("/fusionar")
    public ResponseEntity<?> fusionarMesas(@PathVariable int barId, @RequestBody Map<String, String> mesas) {
        String principal = mesas.get("mesaPrincipalCodigo");
        String secundaria = mesas.get("mesaSecundariaCodigo");

        List<Mesa> mesasList = mesaRepository.findByBarId(barId);
        Mesa mesaPrincipal = null;
        Mesa mesaSecundaria = null;

        for (Mesa m : mesasList) {
            if (m.getCodigo().equals(principal)) mesaPrincipal = m;
            if (m.getCodigo().equals(secundaria)) mesaSecundaria = m;
        }

        if (mesaPrincipal == null || mesaSecundaria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Una o ambas mesas no encontradas");
        }

        mesaSecundaria.setFusionadaCon(principal);
        mesaRepository.save(mesaSecundaria);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/desfusionar/{codigo}")
    public ResponseEntity<List<Mesa>> desfusionar(@PathVariable int barId, @PathVariable String codigo) {
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


    @DeleteMapping("/{codigoMesa}")
    public ResponseEntity<?> eliminarMesa(
            @PathVariable Long barId,
            @PathVariable String codigoMesa) {

        Optional<Bar> barOptional = barRepository.findById(barId);
        if (barOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bar bar = barOptional.get();

        Mesa mesaAEliminar = bar.getMesas().stream()
                .filter(m -> m.getCodigo().equals(codigoMesa))
                .findFirst()
                .orElse(null);

        if (mesaAEliminar == null) {
            return ResponseEntity.notFound().build();
        }

        // Verifica que no tenga mesas fusionadas con ella
        boolean tieneFusionadas = bar.getMesas().stream()
                .anyMatch(m -> codigoMesa.equals(m.getFusionadaCon()));
        if (tieneFusionadas) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar: hay mesas fusionadas con esta.");
        }

        // Elimina la mesa
        bar.getMesas().remove(mesaAEliminar);
        barRepository.save(bar);

        return ResponseEntity.ok().build();
    }


}
