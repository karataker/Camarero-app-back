
package com.es.tfm.ms_camarero_reservas.service;

import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.model.Reserva;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import com.es.tfm.ms_camarero_reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

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
        Mesa mesa = mesaRepository.findById(reserva.getMesa().getId()).orElseThrow();
        reserva.setMesa(mesa);
        return reservaRepository.save(reserva);
    }
}