package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.Ocorrencia;
import com.gisa.gisaapplication.service.OcorrenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    // Listar todas as ocorrências
    @GetMapping
    public ResponseEntity<List<Ocorrencia>> listarTodas() {
        return ResponseEntity.ok(ocorrenciaService.listarTodas());
    }

    // Buscar ocorrência por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ocorrenciaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Buscar ocorrências com filtros
    @GetMapping("/filtros")
    public ResponseEntity<List<Ocorrencia>> buscarComFiltros(
            @RequestParam(required = false) Integer obraId,
            @RequestParam(required = false) Integer statusId) {
        return ResponseEntity.ok(ocorrenciaService.buscarComFiltros(obraId, statusId));
    }

    // Criar nova ocorrência
    @PostMapping
    public ResponseEntity<Ocorrencia> criarOcorrencia(@RequestBody Ocorrencia ocorrencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaService.salvarOcorrencia(ocorrencia));
    }

    // Atualizar ocorrência existente
    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizarOcorrencia(@PathVariable Integer id, @RequestBody Ocorrencia ocorrencia) {
        try {
            return ResponseEntity.ok(ocorrenciaService.atualizarOcorrencia(id, ocorrencia));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Excluir ocorrência por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOcorrencia(@PathVariable Integer id) {
        try {
            ocorrenciaService.excluirOcorrencia(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
