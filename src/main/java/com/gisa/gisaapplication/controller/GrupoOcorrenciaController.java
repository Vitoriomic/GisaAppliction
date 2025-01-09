package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.GrupoOcorrencia;
import com.gisa.gisaapplication.service.GrupoOcorrenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupo-ocorrencia")
public class GrupoOcorrenciaController {

    private final GrupoOcorrenciaService grupoOcorrenciaService;

    public GrupoOcorrenciaController(GrupoOcorrenciaService grupoOcorrenciaService) {
        this.grupoOcorrenciaService = grupoOcorrenciaService;
    }

    @GetMapping
    public ResponseEntity<List<GrupoOcorrencia>> listarTodos() {
        return ResponseEntity.ok(grupoOcorrenciaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoOcorrencia> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(grupoOcorrenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<GrupoOcorrencia> salvarGrupoOcorrencia(@RequestBody GrupoOcorrencia grupoOcorrencia) {
        grupoOcorrenciaService.salvarGrupoOcorrencia(grupoOcorrencia);
        return ResponseEntity.ok(grupoOcorrencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupoOcorrencia(@PathVariable Integer id) {
        grupoOcorrenciaService.excluirGrupoOcorrencia(id);
        return ResponseEntity.noContent().build();
    }
}
