package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.GravidadeOcorrencia;
import com.gisa.gisaapplication.service.GravidadeOcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gravidade-ocorrencias")
public class GravidadeOcorrenciaController {

    @Autowired
    private GravidadeOcorrenciaService service;

    @GetMapping
    public List<GravidadeOcorrencia> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GravidadeOcorrencia> getById(@PathVariable Integer id) {
        Optional<GravidadeOcorrencia> gravidadeOcorrencia = service.findById(id);
        return gravidadeOcorrencia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public GravidadeOcorrencia create(@RequestBody GravidadeOcorrencia gravidadeOcorrencia) {
        return service.save(gravidadeOcorrencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GravidadeOcorrencia> update(@PathVariable Integer id, @RequestBody GravidadeOcorrencia gravidadeOcorrencia) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        gravidadeOcorrencia.setGravidadeId(id);
        return ResponseEntity.ok(service.save(gravidadeOcorrencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
