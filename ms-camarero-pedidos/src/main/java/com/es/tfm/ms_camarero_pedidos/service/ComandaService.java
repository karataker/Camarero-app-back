package com.es.tfm.ms_camarero_pedidos.service;

import com.es.tfm.ms_camarero_pedidos.client.FacturacionClient;
import com.es.tfm.ms_camarero_pedidos.client.InventarioClient;
import com.es.tfm.ms_camarero_pedidos.client.MenuClient;
import com.es.tfm.ms_camarero_pedidos.client.NotificacionClient;
import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import com.es.tfm.ms_camarero_pedidos.model.ItemComanda;
import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.repository.ComandaRepository;
import com.es.tfm.ms_camarero_pedidos.repository.ItemComandaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final ItemComandaRepository itemComandaRepository;
    private final FacturacionClient facturacionClient;
    private final InventarioClient inventarioClient;
    private final MenuClient menuClient;
    private final NotificacionClient notificacionClient;

    public ComandaService(ComandaRepository comandaRepository,
                          ItemComandaRepository itemComandaRepository,
                          FacturacionClient facturacionClient,
                          InventarioClient inventarioClient,
                          MenuClient menuClient,
                          NotificacionClient notificacionClient) {
        this.comandaRepository = comandaRepository;
        this.itemComandaRepository = itemComandaRepository;
        this.facturacionClient = facturacionClient;
        this.inventarioClient = inventarioClient;
        this.menuClient = menuClient;
        this.notificacionClient = notificacionClient;
    }


    public void procesarComanda(ComandaDTO dto) {
        // 1. Guardar comanda
        Comanda comanda = new Comanda();
        comanda.setBarId(dto.getBarId()); // barId es Integer
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
        notificacionClient.enviarNotificacion("pedido", "Nueva comanda en cocina para mesa " + dto.getMesaCodigo(), dto.getBarId()); // barId es Integer

        // 3. Actualizar stock en inventario
        items.forEach(item -> {
            if (item.getProductoId() != null) {
                inventarioClient.restarIngredientes(item.getProductoId(), item.getCantidad());
            }
        });

        // 4. Crear factura en micro de facturación
        facturacionClient.crearFacturaDesdeComanda(dto);
    }


    // JM añadido para funcionamiento de llamada getComandasPorMesa
    public List<Comanda> getComandasPorMesa(Integer barId, String mesaCodigo) { // barId es Integer
        return comandaRepository.findByBarIdAndMesaCodigo(barId, mesaCodigo);
    }

    // Nuevo metodo 08/06/2025
    public List<Comanda> getComandasPorBar(Integer barId) { // barId es Integer
        return comandaRepository.findByBarId(barId);
    }

    public ItemComanda actualizarEstadoItem(Integer itemId, String nuevoEstado) {
        ItemComanda item = itemComandaRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el item con ID: " + itemId));
        item.setEstado(nuevoEstado);
        return itemComandaRepository.save(item);
    }

}