
package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByBarId(Long barId);
    List<Mesa> findByBarIdAndZonaAndEstado(Integer barId, String zona, String estado);
}
