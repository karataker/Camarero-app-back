package com.es.tfm.ms_camarero_pedidos.service;

import com.es.tfm.ms_camarero_pedidos.client.*;
import com.es.tfm.ms_camarero_pedidos.model.Comanda;
import com.es.tfm.ms_camarero_pedidos.model.ItemComanda;
import com.es.tfm.ms_camarero_pedidos.model.dto.ComandaDTO;
import com.es.tfm.ms_camarero_pedidos.repository.ComandaRepository;
import com.es.tfm.ms_camarero_pedidos.repository.ItemComandaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
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
    private final MesaClient mesaClient;

    public ComandaService(ComandaRepository comandaRepository,
                          ItemComandaRepository itemComandaRepository,
                          FacturacionClient facturacionClient,
                          InventarioClient inventarioClient,
                          MenuClient menuClient,
                          NotificacionClient notificacionClient,
                          MesaClient mesaClient) {
        this.comandaRepository = comandaRepository;
        this.itemComandaRepository = itemComandaRepository;
        this.facturacionClient = facturacionClient;
        this.inventarioClient = inventarioClient;
        this.menuClient = menuClient;
        this.notificacionClient = notificacionClient;
        this.mesaClient = mesaClient;
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

        // Obtener el nombre/código de la mesa real desde el microservicio de reservas
        String nombreMesa = mesaClient.obtenerCodigoMesa(dto.getBarId(), Integer.valueOf(dto.getMesaCodigo()));


        // 2. Avisar a notificaciones
        notificacionClient.enviarNotificacion("pedido", "Nueva comanda en cocina para mesa " + nombreMesa, dto.getBarId());

        // 3. Actualizar stock en inventario
        items.forEach(item -> {
            if (item.getProductoId() != null) {
                inventarioClient.restarIngredientes(item.getProductoId(), item.getCantidad());
            }
        });

        // 4. Crear factura en micro de facturación
        facturacionClient.crearFacturaDesdeComanda(dto);
    }


    public List<Comanda> getComandasPorMesa(Integer barId, String mesaCodigo) {
        return comandaRepository.findByBarIdAndMesaCodigo(barId, mesaCodigo);
    }

    public List<Comanda> getComandasPorBar(Integer barId) {
        return comandaRepository.findByBarId(barId);
    }

    public ItemComanda actualizarEstadoItem(Integer itemId, String nuevoEstado) {
        ItemComanda item = itemComandaRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el item con ID: " + itemId));

        item.setEstado(nuevoEstado);
        ItemComanda actualizado = itemComandaRepository.save(item);

        Comanda comanda = actualizado.getComanda();
        actualizarEstadoComanda(comanda); // recalcula y guarda el estado de la comanda

        return actualizado;
    }

    public void actualizarEstadoComanda(Comanda comanda) {
        List<String> prioridad = List.of("pendiente", "en_preparacion", "listo", "entregado", "terminado");

        String estadoCalculado = comanda.getItems().stream()
                .map(ItemComanda::getEstado)
                .min(Comparator.comparingInt(prioridad::indexOf))
                .orElse("pendiente");

        comanda.setEstado(estadoCalculado);
        comandaRepository.save(comanda);
    }

    public void marcarComandasComoTerminadas(Integer barId, String mesaCodigo) {
        List<Comanda> comandas = comandaRepository.findByBarIdAndMesaCodigo(barId, mesaCodigo);

        for (Comanda comanda : comandas) {
            for (ItemComanda item : comanda.getItems()) {
                item.setEstado("terminado");
            }
            comanda.setEstado("terminado");
            comandaRepository.save(comanda);
        }
    }
}