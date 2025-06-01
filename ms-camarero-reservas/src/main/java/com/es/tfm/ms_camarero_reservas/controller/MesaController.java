package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import com.es.tfm.ms_camarero_reservas.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mesas") // MODIFICADO: Endpoint base cambiado
public class MesaController {

    private final MesaRepository mesaRepository;
    private final BarRepository barRepository;

    public MesaController(MesaRepository mesaRepository, BarRepository barRepository) {
        this.mesaRepository = mesaRepository;
        this.barRepository = barRepository;
    }

    // MODIFICADO: barId ahora es un @RequestParam
    // Ruta: GET /api/mesas?barId={barId}
    @GetMapping
    public List<Mesa> getMesas(@RequestParam(required = false) Long barId) {
        if (barId != null) {
            return mesaRepository.findByBarId(barId);
        } else {
            // Asumiendo que tienes un método findAll() o similar en tu MesaRepository
            // y que este método devuelve las mesas con la información del bar cargada.
            return mesaRepository.findAll();
        }
    }
    @PostMapping
    public Mesa createMesa(@RequestBody Mesa mesa) {
        if (mesa.getBar() == null || mesa.getBar().getId() == null) {
            // Considera lanzar una excepción más específica o devolver un ResponseEntity con error
            throw new IllegalArgumentException("El ID del bar es necesario para crear una mesa.");
        }
        Bar bar = barRepository.findById(mesa.getBar().getId())
                .orElseThrow(() -> new RuntimeException("Bar no encontrado con id: " + mesa.getBar().getId())); // O una excepción más adecuada
        mesa.setBar(bar);
        return mesaRepository.save(mesa);
    }

    // MODIFICADO: barId ya no es un @PathVariable.
    // Se espera que barId venga en el cuerpo del JSON.
    // El cliente debe enviar: {"barId": "1", "mesaPrincipalCodigo": "A1", "mesaSecundariaCodigo": "A2"}
    // Ruta: PUT /api/mesas/fusionar
    @PutMapping("/fusionar")
    public ResponseEntity<?> fusionarMesas(@RequestBody Map<String, String> payload) {
        String barIdStr = payload.get("barId");
        String principal = payload.get("mesaPrincipalCodigo");
        String secundaria = payload.get("mesaSecundariaCodigo");

        if (barIdStr == null || principal == null || secundaria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan parámetros: barId, mesaPrincipalCodigo o mesaSecundariaCodigo");
        }

        Long barId;
        try {
            barId = Long.parseLong(barIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("barId debe ser un número válido.");
        }

        List<Mesa> mesasList = mesaRepository.findByBarId(barId);
        Mesa mesaPrincipal = null;
        Mesa mesaSecundaria = null;

        for (Mesa m : mesasList) {
            if (m.getCodigo().equals(principal)) mesaPrincipal = m;
            if (m.getCodigo().equals(secundaria)) mesaSecundaria = m;
        }

        if (mesaPrincipal == null || mesaSecundaria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Una o ambas mesas no encontradas para el barId proporcionado");
        }

        mesaSecundaria.setFusionadaCon(principal);
        mesaRepository.save(mesaSecundaria);

        return ResponseEntity.ok().build();
    }

    // MODIFICADO: barId ahora es un @RequestParam
    // Ruta: PUT /api/mesas/desfusionar/{codigo}?barId={barId}
    @PutMapping("/desfusionar/{codigo}")
    public ResponseEntity<List<Mesa>> desfusionar(@RequestParam Long barId, @PathVariable String codigo) {
        List<Mesa> mesas = mesaRepository.findByBarId(barId);

        for (Mesa mesa : mesas) {
            if (codigo.equals(mesa.getFusionadaCon())) {
                mesa.setFusionadaCon(null);
                mesaRepository.save(mesa);
            }
            if (codigo.equals(mesa.getCodigo())) {
                mesa.setDisponible(true);
                mesa.setComensales(0);
                mesa.setPedidoEnviado(false);
                mesa.setFusionadaCon(null); // Asegura que la mesa principal también se desfusione si es el caso
                mesaRepository.save(mesa);
            }
        }

        List<Mesa> actualizadas = mesaRepository.findByBarId(barId);
        return ResponseEntity.ok(actualizadas);
    }

    // MODIFICADO: barId ahora es un @RequestParam
    // Ruta: DELETE /api/mesas/{codigoMesa}?barId={barId}
    @DeleteMapping("/{codigoMesa}")
    public ResponseEntity<?> eliminarMesa(
            @RequestParam Long barId, // MODIFICADO
            @PathVariable String codigoMesa) {

        Optional<Bar> barOptional = barRepository.findById(barId);
        if (barOptional.isEmpty()) {
            // Devolver un error más específico, por ejemplo, Bar no encontrado.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bar no encontrado con id: " + barId);
        }

        Bar bar = barOptional.get();

        // Filtrar las mesas del bar específico.
        // Nota: mesaRepository.findByBarId(barId) podría ser más eficiente si la lista de mesas en Bar no está ya cargada.
        // Sin embargo, la lógica actual opera sobre bar.getMesas(), lo que asume que están cargadas.
        Mesa mesaAEliminar = bar.getMesas().stream()
                .filter(m -> m.getCodigo().equals(codigoMesa))
                .findFirst()
                .orElse(null);

        if (mesaAEliminar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa con código " + codigoMesa + " no encontrada en el bar " + barId);
        }

        // Verifica que no tenga mesas fusionadas con ella
        // Esta comprobación debe hacerse sobre las mesas del bar correspondiente
        boolean tieneFusionadas = mesaRepository.findByBarId(barId).stream()
                .anyMatch(m -> codigoMesa.equals(m.getFusionadaCon()));
        if (tieneFusionadas) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar: hay mesas fusionadas con esta.");
        }

        // Elimina la mesa de la colección del Bar y del repositorio
        bar.getMesas().remove(mesaAEliminar);
        // barRepository.save(bar); // Esto podría no ser suficiente si la relación es bidireccional y Mesa es la dueña.
        // O si se espera una eliminación en cascada configurada de otra manera.
        // Para eliminar la mesa de la base de datos, es más directo:
        mesaRepository.delete(mesaAEliminar);


        return ResponseEntity.ok().build();
    }
}