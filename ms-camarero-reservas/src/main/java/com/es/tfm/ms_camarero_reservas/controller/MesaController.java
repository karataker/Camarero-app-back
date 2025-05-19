
package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Mesa> getMesas(@PathVariable Long barId) {
        return mesaRepository.findByBarId(barId);
    }

    @PostMapping
    public Mesa createMesa(@PathVariable Long barId, @RequestBody Mesa mesa) {
        Bar bar = barRepository.findById(barId).orElseThrow();
        mesa.setBar(bar);
        return mesaRepository.save(mesa);
    }

    @PutMapping("/fusionar")
    public ResponseEntity<?> fusionarMesas(@PathVariable Long barId, @RequestBody Map<String, String> mesas) {
        String principal = mesas.get("mesaPrincipalCodigo");
        String secundaria = mesas.get("mesaSecundariaCodigo");

        List<Mesa> mesasList = mesaRepository.findByBarId(barId);
        Mesa mesaPrincipal = null;
        Mesa mesaSecundaria = null;

        for (Mesa m : mesasList) {
            if (m.getNombre().equals(principal)) mesaPrincipal = m;
            if (m.getNombre().equals(secundaria)) mesaSecundaria = m;
        }

        if (mesaPrincipal == null || mesaSecundaria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Una o ambas mesas no encontradas");
        }

        mesaSecundaria.setFusionadaCon(principal);
        mesaRepository.save(mesaSecundaria);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/desfusionar/{codigo}")
    public ResponseEntity<?> desfusionar(@PathVariable Long barId, @PathVariable String codigo) {
        List<Mesa> mesas = mesaRepository.findByBarId(barId);
        for (Mesa mesa : mesas) {
            if (codigo.equals(mesa.getFusionadaCon())) {
                mesa.setFusionadaCon(null);
                mesaRepository.save(mesa);
            }
        }
        return ResponseEntity.ok().build();
    }
}
