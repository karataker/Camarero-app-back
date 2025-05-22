package com.es.tfm.ms_camarero_reservas.service;

import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import com.es.tfm.ms_camarero_reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, MesaRepository mesaRepository) {
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        // Asumiendo que agregaste este metodo personalizado en MesaRepository
        List<Mesa> disponibles = mesaRepository.findByBarIdAndZonaAndDisponibleTrue(
                reserva.getMesa().getBar().getId(), reserva.getMesa().getZona()
        );

        List<Mesa> seleccionadas = new ArrayList<>();
        int totalCapacidad = 0;

        for (Mesa mesa : disponibles) {
            if (totalCapacidad < reserva.getNumeroComensales()) {
                seleccionadas.add(mesa);
                totalCapacidad += mesa.getCapacidad();
            }
        }

        if (totalCapacidad < reserva.getNumeroComensales()) {
            throw new RuntimeException("No hay mesas suficientes disponibles");
        }

        String codigoPrincipal = seleccionadas.get(0).getCodigo(); // antes getNombre()

        for (Mesa mesa : seleccionadas) {
            mesa.setDisponible(false); // en lugar de setEstado("reservada")
            mesa.setFusionadaCon(
                    mesa.equals(seleccionadas.get(0)) ? null : codigoPrincipal
            );
            mesaRepository.save(mesa);
        }

        reserva.setMesa(seleccionadas.get(0));
        return reservaRepository.save(reserva);
    }
}
