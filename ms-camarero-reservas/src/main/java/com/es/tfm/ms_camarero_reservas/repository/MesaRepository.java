// In MesaRepository.java (assuming it exists)
package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByBarId(Long barId);
    Optional<Mesa> findByBarIdAndCodigo(Long barId, String codigo);
    Mesa findByBarIdAndId(int barId, int id);

    List<Mesa> id(Integer id);
}