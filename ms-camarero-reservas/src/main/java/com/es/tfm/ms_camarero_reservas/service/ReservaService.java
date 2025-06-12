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
    private final MesaRepository mesaRepository;
    private final BarRepository barRepository;

    public ReservaService(ReservaRepository reservaRepository, MesaRepository mesaRepository, BarRepository barRepository) {
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
        this.barRepository = barRepository;
    }

    public Reserva create(Reserva reserva) {
        if (reserva.getBar() != null && reserva.getBar().getId() != null) {
            Bar bar = barRepository.findById(reserva.getBar().getId())
                    .orElseThrow(() -> new RuntimeException("Bar not found with ID: " + reserva.getBar().getId()));
            reserva.setBar(bar);
        } else {
            throw new IllegalArgumentException("Bar ID is required for creating a reservation.");
        }
        reserva.setMesa(null);

        reserva.setEstado("pendiente");
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
        if (cambios.getMesa() != null && cambios.getMesa().getId() != null) {
            Mesa newMesa = mesaRepository.findById(cambios.getMesa().getId())
                    .orElseThrow(() -> new RuntimeException("Mesa not found with ID: " + cambios.getMesa().getId()));
            r.setMesa(newMesa);
        } else if (cambios.getMesa() != null && cambios.getMesa().getId() == null) {
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