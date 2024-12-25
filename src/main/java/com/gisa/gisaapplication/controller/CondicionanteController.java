package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.Condicionante;
import com.gisa.gisaapplication.service.CondicionanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condicionantes")
public class CondicionanteController {

    private final CondicionanteService condicionanteService;

    public CondicionanteController(CondicionanteService condicionanteService) {
        this.condicionanteService = condicionanteService;
    }

    // Endpoint para buscar condicionantes com filtros
    @GetMapping
    public List<Condicionante> buscarCondicionantesComFiltros(
            @RequestParam(required = false) Integer obraId,
            @RequestParam(required = false) Integer statusId) {
        return condicionanteService.buscarComFiltros(obraId, statusId);
    }

    // Buscar condicionante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Condicionante> buscarPorId(@PathVariable int id) {
        try {
            Condicionante condicionante = condicionanteService.buscarPorId(id);
            return ResponseEntity.ok(condicionante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Condicionante> criarCondicionante(@RequestBody Condicionante condicionante) {
        try {
            Condicionante condicionanteSalva = condicionanteService.salvar(condicionante);
            return ResponseEntity.status(HttpStatus.CREATED).body(condicionanteSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCondicionante(@PathVariable int id, @RequestBody Condicionante condicionanteAtualizada) {
        try {
            Condicionante condicionanteExistente = condicionanteService.buscarPorId(id);
            // Atualiza os campos necessários
            condicionanteExistente.setObra(condicionanteAtualizada.getObra());
            condicionanteExistente.setCondicionante(condicionanteAtualizada.getCondicionante());
            condicionanteExistente.setIdentificacao(condicionanteAtualizada.getIdentificacao());
            condicionanteExistente.setStatusCondicionante(condicionanteAtualizada.getStatusCondicionante());
            condicionanteExistente.setComprovacao(condicionanteAtualizada.getComprovacao());
            condicionanteExistente.setPrazoVencimento(condicionanteAtualizada.getPrazoVencimento());
            condicionanteExistente.setAcaoAtendimento(condicionanteAtualizada.getAcaoAtendimento());
            condicionanteExistente.setSituacao(condicionanteAtualizada.getSituacao());
            condicionanteExistente.setProtocolacao(condicionanteAtualizada.getProtocolacao());
            condicionanteExistente.setResponsabilidadeTerceiros(condicionanteAtualizada.getResponsabilidadeTerceiros());
            condicionanteExistente.setResponsabilidadeCliente(condicionanteAtualizada.getResponsabilidadeCliente());
            condicionanteExistente.setResponsabilidadeZago(condicionanteAtualizada.getResponsabilidadeZago());
            condicionanteExistente.setDataAtendimento(condicionanteAtualizada.getDataAtendimento());

            // Salva a atualização
            condicionanteService.salvar(condicionanteExistente);
            return ResponseEntity.ok("Condicionante atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

