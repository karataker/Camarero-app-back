package com.es.tfm.ms_camarero_reservas.controller;

import com.es.tfm.ms_camarero_reservas.model.Bar;
import com.es.tfm.ms_camarero_reservas.model.Mesa;
import com.es.tfm.ms_camarero_reservas.repository.BarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bares")
public class BarController {

    private final BarRepository barRepository;

    public BarController(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    @GetMapping
    public List<Bar> getAllBares() {
        return barRepository.findAll();
    }
}
