package com.es.tfm.ms_camarero_reservas.service;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.repository.ReservaRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository; // Keep if needed for other operations, but won't be used in create for initial reservation
    private final BarRepository barRepository;

    public ReservaService(ReservaRepository reservaRepository, MesaRepository mesaRepository, BarRepository barRepository) {
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
        this.barRepository = barRepository;
    }

    public Reserva create(Reserva reserva) {
        // Fetch and set Bar entity - This is still required as it's sent from the client
        if (reserva.getBar() != null && reserva.getBar().getId() != null) {
            Bar bar = barRepository.findById(reserva.getBar().getId())
                    .orElseThrow(() -> new RuntimeException("Bar not found with ID: " + reserva.getBar().getId()));
            reserva.setBar(bar);
        } else {
            // This error should still be thrown if no bar ID is provided from the client
            throw new IllegalArgumentException("Bar ID is required for creating a reservation.");
        }

        // --- MODIFICATION START ---
        // REMOVE the Mesa ID check and assignment for initial creation
        // The Mesa will be assigned later from the admin view.
        reserva.setMesa(null); // Explicitly set mesa to null if not provided, or simply don't set it if it's already null
        // --- MODIFICATION END ---

        // Set initial status and request date
        reserva.setEstado("pendiente"); // Change to "pendiente" as the mesa is not yet confirmed
        reserva.setFechaSolicitud(LocalDateTime.now());

        return reservaRepository.save(reserva);
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> getReservasByBarId(Long barId) {
        return reservaRepository.findByBar_Id(barId);
    }

    public Reserva update(Long id, Reserva cambios) {
        Reserva r = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe"));

        if (cambios.getEstado() != null) r.setEstado(cambios.getEstado());
        // Handle Mesa update: if a new mesa is provided by ID, fetch and set it
        // This part remains as it's likely used for admin updates
        if (cambios.getMesa() != null && cambios.getMesa().getId() != null) {
            Mesa newMesa = mesaRepository.findById(cambios.getMesa().getId())
                    .orElseThrow(() -> new RuntimeException("Mesa not found with ID: " + cambios.getMesa().getId()));
            r.setMesa(newMesa);
        } else if (cambios.getMesa() != null && cambios.getMesa().getId() == null) {
            // If Mesa object is sent but ID is null, assume intent to clear mesa
            r.setMesa(null);
        }


        if (cambios.getNombreCliente() != null) r.setNombreCliente(cambios.getNombreCliente());
        if (cambios.getCorreoElectronico() != null) r.setCorreoElectronico(cambios.getCorreoElectronico());
        if (cambios.getTelefono() != null) r.setTelefono(cambios.getTelefono());
        if (cambios.getNumeroComensales() > 0) r.setNumeroComensales(cambios.getNumeroComensales());
        if (cambios.getZonaPreferida() != null) r.setZonaPreferida(cambios.getZonaPreferida());
        if (cambios.getFechaHora() != null) r.setFechaHora(cambios.getFechaHora());
        if (cambios.getMensaje() != null) r.setMensaje(cambios.getMensaje());

        return reservaRepository.save(r);
    }

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}