package com.es.tfm.ms_camarero_pedidos.service;

import com.es.tfm.ms_camarero_pedidos.client.FacturacionClient;
import com.es.tfm.ms_camarero_pedidos.client.InventarioClient;
import com.es.tfm.ms_camarero_pedidos.client.MenuClient;
import com.es.tfm.ms_camarero_pedidos.client.NotificacionClient;

import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import com.es.tfm.ms_camarero_pedidos.model.ItemComanda;
import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.repository.ComandaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final FacturacionClient facturacionClient;
    private final InventarioClient inventarioClient;
    private final MenuClient menuClient;
    private final NotificacionClient notificacionClient;

    public ComandaService(ComandaRepository comandaRepository,
                          FacturacionClient facturacionClient,
                          InventarioClient inventarioClient,
                          MenuClient menuClient,
                          NotificacionClient notificacionClient) {
        this.comandaRepository = comandaRepository;
        this.facturacionClient = facturacionClient;
        this.inventarioClient = inventarioClient;
        this.menuClient = menuClient;
        this.notificacionClient = notificacionClient;
    }


    public void procesarComanda(ComandaDTO dto) {
        // 1. Guardar comanda
        Comanda comanda = new Comanda();
        comanda.setBarId(dto.getBarId());
        comanda.setMesaCodigo(dto.getMesaCodigo());
        comanda.setFecha(LocalDateTime.now());

        List<ItemComanda> items = dto.getItems().stream().map(i -> {
            ItemComanda item = new ItemComanda();
            item.setNombre(i.getNombre());
            item.setCantidad(i.getCantidad());
            item.setProductoId(i.getProductoId());
            item.setPrecio(i.getPrecio());
            item.setComanda(comanda);
            return item;
        }).collect(Collectors.toList());

        comanda.setItems(items);
        comandaRepository.save(comanda);

        // 2. Avisar a notificaciones
        notificacionClient.enviarNotificacion("pedido", "Nueva comanda en cocina para mesa " + dto.getMesaCodigo(), dto.getBarId());

        // 3. Actualizar stock en inventario
        items.forEach(item -> {
            if (item.getProductoId() != null) {
                inventarioClient.restarIngredientes(item.getProductoId(), item.getCantidad());
            }
        });

        // 4. Crear factura en micro de facturación
        facturacionClient.crearFacturaDesdeComanda(dto);
    }
}


