package com.es.tfm.ms_camarero_pedidos.repository;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaRepository extends JpaRepository<Comanda, Integer> {
}
