package com.es.tfm.ms_camarero_pedidos.repository;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {
    List<Comanda> findByBarIdAndMesaCodigo(Integer barId, String mesaCodigo);
    List<Comanda> findByBarId(Integer barId);
}
