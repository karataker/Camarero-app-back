
package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByBarId(int barId);

    List<Mesa> findByBarIdAndZonaAndDisponibleTrue(Integer barId, String zona);
}