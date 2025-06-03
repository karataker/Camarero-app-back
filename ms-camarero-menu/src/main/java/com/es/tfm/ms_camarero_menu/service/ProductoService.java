package com.es.tfm.ms_camarero_menu.service;

import com.es.tfm.ms_camarero_menu.client.InventarioClient;
import com.es.tfm.ms_camarero_menu.model.IngredienteMenu;
import com.es.tfm.ms_camarero_menu.model.Producto;
import com.es.tfm.ms_camarero_menu.repository.IngredienteMenuRepository;
import com.es.tfm.ms_camarero_menu.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final IngredienteMenuRepository ingredienteMenuRepository;
    private final InventarioClient inventarioClient;

    public ProductoService(ProductoRepository productoRepository, IngredienteMenuRepository ingredienteMenuRepository, InventarioClient inventarioClient) {
        this.productoRepository = productoRepository;
        this.ingredienteMenuRepository = ingredienteMenuRepository;
        this.inventarioClient = inventarioClient;
    }


    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    public Producto getById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> getByBar(Integer barId) {
        return productoRepository.findByBarId(barId);
    }

    public Producto save(Producto producto) {
        if (producto.getIngredientes() != null) {
            for (IngredienteMenu ing : producto.getIngredientes()) {
                ing.setProductoMenu(producto);
            }
        }
        return productoRepository.save(producto);
    }

    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    public int calcularRacionesDisponibles(Producto producto) {
        if (producto.getInventarioIdDirecto() != null) {
            return (int) inventarioClient.getStockActual(producto.getInventarioIdDirecto());
        }
        if (producto.getIngredientes() == null || producto.getIngredientes().isEmpty()) return 0;

        int raciones = Integer.MAX_VALUE;
        for (IngredienteMenu ing : producto.getIngredientes()) {
            double stockActual = inventarioClient.getStockActual(ing.getProductoInventarioId());
            double cantidad = ing.getCantidadPorRacion();
            if (cantidad == 0) continue;
            int posibles = (int) (stockActual / cantidad);
            raciones = Math.min(raciones, posibles);
        }
        return raciones;
    }
}