package com.es.tfm.ms_camarero_analiticas.service;

import com.es.tfm.ms_camarero_analiticas.client.FacturacionClient;
import com.es.tfm.ms_camarero_analiticas.client.PedidosClient;
import com.es.tfm.ms_camarero_analiticas.client.ReservasClient;
import com.es.tfm.ms_camarero_analiticas.dto.MesValorDTO;
import com.es.tfm.ms_camarero_analiticas.dto.ProductoRankingDTO;
import com.es.tfm.ms_camarero_analiticas.dto.ResumenAnaliticoDTO;
import com.es.tfm.ms_camarero_analiticas.model.*;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnaliticasService {

    private final PedidosClient pedidosClient;
    private final ReservasClient reservasClient;
    private final FacturacionClient facturacionClient;

    public AnaliticasService(PedidosClient pedidosClient, ReservasClient reservasClient, FacturacionClient facturacionClient) {
        this.pedidosClient = pedidosClient;
        this.reservasClient = reservasClient;
        this.facturacionClient = facturacionClient;
    }

    public ResumenAnaliticoDTO generarResumen(int barId, int ano) {
        List<Comanda> comandas = pedidosClient.obtenerComandasPorBar(barId);
        List<Reserva> reservas = reservasClient.obtenerReservasPorBar(barId);
        List<Factura> facturas = facturacionClient.getFacturasPorBarYAnio(barId, ano);

        Map<String, Double> ventasEstimadas = new HashMap<>();
        Map<String, Double> ventasReales = new HashMap<>();
        Map<String, Integer> pedidosPorMes = new HashMap<>();
        Map<String, List<Double>> carritoEstimado = new HashMap<>();
        Map<String, List<Double>> carritoReal = new HashMap<>();
        Map<String, Integer> reservasPorMes = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();

        for (Comanda comanda : comandas) {
            if (comanda.getFecha().getYear() != ano) continue;
            String mes = comanda.getFecha().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            double total = comanda.getItems().stream().mapToDouble(i -> i.getCantidad() * i.getPrecio()).sum();
            ventasEstimadas.merge(mes, total, Double::sum);
            pedidosPorMes.merge(mes, 1, Integer::sum);
            carritoEstimado.computeIfAbsent(mes, k -> new ArrayList<>()).add(total);
            for (ItemComanda item : comanda.getItems()) {
                productosVendidos.merge(item.getNombre(), item.getCantidad(), Integer::sum);
            }
        }

        for (Factura factura : facturas) {
            if (factura.getFecha().getYear() != ano) continue;
            String mes = factura.getFecha().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            ventasReales.merge(mes, factura.getTotal(), Double::sum);
            carritoReal.computeIfAbsent(mes, k -> new ArrayList<>()).add(factura.getTotal());
            for (LineaFactura linea : factura.getLineas()) {
                productosVendidos.merge(linea.getNombreProducto(), linea.getCantidad(), Integer::sum);
            }
        }

        for (Reserva reserva : reservas) {
            if (reserva.getFechaHora().getYear() != ano) continue;
            String mes = reserva.getFechaHora().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            reservasPorMes.merge(mes, 1, Integer::sum);
        }

        List<MesValorDTO> ventasEstimadasMensuales = mapToDTO(ventasEstimadas);
        List<MesValorDTO> ventasRealesMensuales = mapToDTO(ventasReales);
        List<MesValorDTO> pedidosMensuales = mapToDTO(pedidosPorMes);
        List<MesValorDTO> reservasMensuales = mapToDTO(reservasPorMes);

        List<MesValorDTO> carritoMedioEstimado = carritoEstimado.entrySet().stream()
                .map(e -> new MesValorDTO(e.getKey(), e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)))
                .collect(Collectors.toList());

        List<MesValorDTO> carritoMedioReal = carritoReal.entrySet().stream()
                .map(e -> new MesValorDTO(e.getKey(), e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)))
                .collect(Collectors.toList());

        List<ProductoRankingDTO> productosTop = productosVendidos.entrySet().stream()
                .map(e -> new ProductoRankingDTO(e.getKey(), e.getValue(), ""))
                .collect(Collectors.toList());

        ResumenAnaliticoDTO resumen = new ResumenAnaliticoDTO();
        resumen.setVentasMensuales(ventasEstimadasMensuales);
        resumen.setVentasRealesMensuales(ventasRealesMensuales);
        resumen.setPedidosMensuales(pedidosMensuales);
        resumen.setReservasMensuales(reservasMensuales);
        resumen.setCarritoMedioEstimado(carritoMedioEstimado);
        resumen.setCarritoMedioReal(carritoMedioReal);
        resumen.setProductosTop(productosTop);
        return resumen;
    }

    private List<MesValorDTO> mapToDTO(Map<String, ? extends Number> data) {
        return data.entrySet().stream()
                .map(e -> new MesValorDTO(e.getKey(), e.getValue().doubleValue()))
                .collect(Collectors.toList());
    }
}