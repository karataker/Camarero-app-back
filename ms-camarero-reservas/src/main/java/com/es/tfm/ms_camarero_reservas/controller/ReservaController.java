
package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.service.ReservaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }
}
