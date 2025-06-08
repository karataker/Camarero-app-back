package com.es.tfm.ms_camarero_pedidos.repository;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {
    // Nuevo metodo 08/06/2025
    List<Comanda> findByBarIdAndMesaCodigo(Integer barId, String mesaCodigo);

    // Nuevo metodo 08/06/2025
    // Busca todas las comandas por el ID del bar (con Integer)
    List<Comanda> findByBarId(Integer barId);
}
