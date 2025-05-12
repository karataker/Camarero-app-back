
package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
