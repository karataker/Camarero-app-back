package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bares") // This remains the same as it's for top-level bar operations
public class BarController {

    private final BarRepository barRepository;

    public BarController(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    @GetMapping
    public List<Bar> getAllBares() {
        return barRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bar> getBarById(@PathVariable Long id) {
        Optional<Bar> barOptional = barRepository.findById(id);
        if (barOptional.isPresent()) {
            return ResponseEntity.ok(barOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}