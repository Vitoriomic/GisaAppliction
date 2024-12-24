package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.Obra;
import com.gisa.gisaapplication.service.ObraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras")
public class ObraController {

    private final ObraService obraService;

    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }

    // Endpoint para listar todas as obras
    @GetMapping
    public List<Obra> listarTodas() {
        return obraService.listarTodas();
    }

    // Endpoint para buscar uma obra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscarPorId(@PathVariable Integer id) {
        try {
            Obra obra = obraService.buscarPorId(id);
            return ResponseEntity.ok(obra);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
