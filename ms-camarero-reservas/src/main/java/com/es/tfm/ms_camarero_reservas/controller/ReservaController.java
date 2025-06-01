// In ReservaController.java

package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    // Remove direct Autowired of repository if service handles all main operations
    // @Autowired
    // private ReservaRepository reservaRepository; // <-- REMOVE THIS LINE

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaService.create(reserva);
    }


    @GetMapping
    public List<Reserva> getReservas(@RequestParam(required = false) Long barId) {
        if (barId != null) {
            return reservaService.getReservasByBarId(barId);
        } else {
            return reservaService.getAllReservas();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }

    @PutMapping("/{id}")
    public Reserva updateReserva(@PathVariable Long id,
                                 @RequestBody Reserva reserva) {
        return reservaService.update(id, reserva);
    }
}