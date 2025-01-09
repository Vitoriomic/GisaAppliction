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
    public ResponseEntity<List<Obra>> listarTodas() {
        return ResponseEntity.ok(obraService.listarTodas());
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

    // Endpoint para salvar uma nova obra
    @PostMapping
    public ResponseEntity<Obra> salvarObra(@RequestBody Obra obra) {
        obraService.salvarObra(obra);
        return ResponseEntity.ok(obra);
    }

    // Endpoint para excluir uma obra por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirObra(@PathVariable Integer id) {
        obraService.excluirObra(id);
        return ResponseEntity.noContent().build();
    }
}
