package com.es.tfm.ms_camarero_facturacion.repository;

import com.es.tfm.ms_camarero_facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Factura.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    /**
     * Busca todas las facturas asociadas a un bar dado.
     *
     * @param barId Identificador del bar
     * @return Lista de facturas
     */
    List<Factura> findByBarId(Integer barId);
}