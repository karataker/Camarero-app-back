
package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
