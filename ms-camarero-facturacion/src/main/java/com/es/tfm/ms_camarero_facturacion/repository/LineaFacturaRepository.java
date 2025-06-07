package com.es.tfm.ms_camarero_facturacion.repository;

import com.es.tfm.ms_camarero_facturacion.model.LineaFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineaFacturaRepository extends JpaRepository<LineaFactura, Integer> {
}