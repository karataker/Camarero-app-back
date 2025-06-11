package com.es.tfm.ms_camarero_analiticas.controller;
import com.es.tfm.ms_camarero_analiticas.dto.ResumenAnaliticoDTO;
import com.es.tfm.ms_camarero_analiticas.service.AnaliticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analiticas")
public class AnaliticasController {

    @Autowired
    private AnaliticasService service;

    @GetMapping("/bar/{barId}/resumen/{ano}")
    public ResponseEntity<ResumenAnaliticoDTO> getResumen(@PathVariable int barId, @PathVariable int ano) {
        return ResponseEntity.ok(service.generarResumen(barId, ano));
    }
}
