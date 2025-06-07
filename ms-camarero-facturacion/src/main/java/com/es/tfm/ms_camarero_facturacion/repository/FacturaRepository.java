package com.es.tfm.ms_camarero_facturacion.repository;


import com.es.tfm.ms_camarero_facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
}