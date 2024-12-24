package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.Protocolacao;
import com.gisa.gisaapplication.repository.ProtocolacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/protocolacoes")
public class ProtocolacaoController {

    private final ProtocolacaoRepository protocolacaoRepository;

    public ProtocolacaoController(ProtocolacaoRepository protocolacaoRepository) {
        this.protocolacaoRepository = protocolacaoRepository;
    }

    // Listar todas as protocolações
    @GetMapping
    public List<Protocolacao> listarTodas() {
        return protocolacaoRepository.findAll();
    }

    // Buscar uma protocolação específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Protocolacao> buscarPorId(@PathVariable int id) {
        return protocolacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
