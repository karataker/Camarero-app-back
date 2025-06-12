package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Base path for all controllers
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/bares/{barId}/reservas")
    public Reserva createReserva(@PathVariable Long barId, @RequestBody Reserva reserva) {
        return reservaService.create(reserva);
    }


    @GetMapping("/bares/{barId}/reservas")
    public List<Reserva> getReservasByBarId(@PathVariable Long barId) {
        return reservaService.getReservasByBarId(barId);
    }


    @GetMapping("/reservas")
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }


    @DeleteMapping("/reservas/{id}")
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }


    @PutMapping("/reservas/{id}")
    public Reserva updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.update(id, reserva);
    }
}