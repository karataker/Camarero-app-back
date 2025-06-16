package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import necesario

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByBar_Id(Long barId);
}