package com.es.tfm.ms_camarero_reservas.repository;

import com.es.tfm.ms_camarero_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import necesario

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Método para buscar reservas por el ID de la entidad Bar asociada
    List<Reserva> findByBar_Id(Long barId);

    // El método Optional<Reserva> findById(int id) fue eliminado.
    // JpaRepository<Reserva, Long> ya proporciona Optional<Reserva> findById(Long id).

    // El método void deleteById(long id) fue eliminado.
    // JpaRepository<Reserva, Long> ya proporciona void deleteById(Long id).
}