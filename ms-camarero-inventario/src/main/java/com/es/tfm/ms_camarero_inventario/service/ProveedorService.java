package com.es.tfm.ms_camarero_inventario.service;

import com.es.tfm.ms_camarero_inventario.model.Proveedor;
import com.es.tfm.ms_camarero_inventario.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository repo) {
        this.proveedorRepository = repo;
    }

    public List<Proveedor> getAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor getById(Integer id) {
        return proveedorRepository.findById(id).orElseThrow();
    }

    public Proveedor create(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor update(Integer id, Proveedor datos) {
        Proveedor actual = getById(id);
        actual.setNombre(datos.getNombre());
        actual.setTelefono(datos.getTelefono());
        actual.setEmail(datos.getEmail());
        return proveedorRepository.save(actual);
    }

    public void delete(Integer id) {
        proveedorRepository.deleteById(id);
    }

    public List<Proveedor> getByBarId(Integer barId) {
        return proveedorRepository.findByBarId(barId);
    }
}
