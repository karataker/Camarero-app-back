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

    // Create a reservation for a specific bar
    // Ruta: POST /api/bares/{barId}/reservas
    @PostMapping("/bares/{barId}/reservas")
    public Reserva createReserva(@PathVariable Long barId, @RequestBody Reserva reserva) {
        // You might want to add logic here to ensure the barId in the path matches the barId in the reserva object
        // Or, set the bar in the service layer using the path variable barId
        return reservaService.create(reserva);
    }

    // Get all reservations for a specific bar
    // Ruta: GET /api/bares/{barId}/reservas
    @GetMapping("/bares/{barId}/reservas")
    public List<Reserva> getReservasByBarId(@PathVariable Long barId) {
        return reservaService.getReservasByBarId(barId);
    }

    // Get all reservations (if needed, though the requirement is bar-specific)
    // Ruta: GET /api/reservas
    @GetMapping("/reservas")
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    // Delete a reservation by ID (assuming reservation ID is unique across bars or barId is not needed for delete)
    // If a reservation ID can clash across bars, you might want /api/bares/{barId}/reservas/{id}
    // Ruta: DELETE /api/reservas/{id}
    @DeleteMapping("/reservas/{id}")
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }

    // Update a reservation by ID
    // Ruta: PUT /api/reservas/{id}
    @PutMapping("/reservas/{id}")
    public Reserva updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.update(id, reserva);
    }
}