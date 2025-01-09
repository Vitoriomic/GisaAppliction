package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.StatusOcorrencia;
import com.gisa.gisaapplication.service.StatusOcorrenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-ocorrencia")
public class StatusOcorrenciaController {

    private final StatusOcorrenciaService statusOcorrenciaService;

    public StatusOcorrenciaController(StatusOcorrenciaService statusOcorrenciaService) {
        this.statusOcorrenciaService = statusOcorrenciaService;
    }

    @GetMapping
    public ResponseEntity<List<StatusOcorrencia>> listarTodos() {
        return ResponseEntity.ok(statusOcorrenciaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusOcorrencia> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(statusOcorrenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<StatusOcorrencia> salvarStatusOcorrencia(@RequestBody StatusOcorrencia statusOcorrencia) {
        statusOcorrenciaService.salvarStatusOcorrencia(statusOcorrencia);
        return ResponseEntity.ok(statusOcorrencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirStatusOcorrencia(@PathVariable Integer id) {
        statusOcorrenciaService.excluirStatusOcorrencia(id);
        return ResponseEntity.noContent().build();
    }
}
