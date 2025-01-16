package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.auth.service.LogService;
import com.gisa.gisaapplication.model.*;
import com.gisa.gisaapplication.repository.*;
import com.gisa.gisaapplication.service.CondicionanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/condicionantes")
public class CondicionanteController {

    private final CondicionanteService condicionanteService;
    private final LogService logService;


    public CondicionanteController(CondicionanteService condicionanteService, LogService logService) {
        this.condicionanteService = condicionanteService;
        this.logService = logService;
    }



    // Endpoint para buscar condicionantes com filtros
    @GetMapping
    public List<Condicionante> buscarCondicionantesComFiltros(
            @RequestParam(required = false) Integer obraId,
            @RequestParam(required = false) String identificacao,
            @RequestParam(required = false) Integer protocolacaoId,
            @RequestParam(required = false) String acaoAtendimento,
            @RequestParam(required = false) List<Integer> statusIds) {
        return condicionanteService.buscarComFiltros(obraId, identificacao, protocolacaoId, acaoAtendimento, statusIds);
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

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ProtocolacaoRepository protocolacaoRepository;

    @Autowired
    private StatusCondicionanteRepository statusCondicionanteRepository;

    @Autowired
    private RespTerceirosRepository respTerceirosRepository;

    @Autowired
    private RespClienteRepository respClienteRepository;

    @Autowired
    private RespZagoRepository respZagoRepository;

    @Autowired
    private CondicionanteRepository condicionanteRepository;


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createCondicionante(@RequestBody Map<String, Object> payload, Authentication authentication) {
        try {
            // Instância da entidade Condicionante
            Condicionante condicionante = new Condicionante();

            // Validação de campos obrigatórios
            List<String> missingFields = new ArrayList<>();

            if (payload.get("condicionante") == null) missingFields.add("condicionante");
            if (payload.get("identificacao") == null) missingFields.add("identificacao");
            if (payload.get("obraId") == null) missingFields.add("obraId");
            if (payload.get("protocolacaoId") == null) missingFields.add("protocolacaoId");
            if (payload.get("statusId") == null) missingFields.add("statusId");
            if (payload.get("respTerceirosId") == null) missingFields.add("respTerceirosId");
            if (payload.get("respClienteId") == null) missingFields.add("respClienteId");
            if (payload.get("respZagoId") == null) missingFields.add("respZagoId");

            // Retornar erro se houver campos obrigatórios ausentes
            if (!missingFields.isEmpty()) {
                return ResponseEntity.badRequest().body("Campos obrigatórios ausentes: " + String.join(", ", missingFields));
            }

            // Preenchendo os campos da entidade Condicionante
            condicionante.setCondicionante(payload.get("condicionante").toString());
            condicionante.setIdentificacao(payload.get("identificacao").toString());

            // Configurar relacionamentos (IDs relacionados)
            Obra obra = obraRepository.findById(Integer.parseInt(payload.get("obraId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada"));
            condicionante.setObra(obra);

            Protocolacao protocolacao = protocolacaoRepository.findById(Integer.parseInt(payload.get("protocolacaoId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Protocolação não encontrada"));
            condicionante.setProtocolacao(protocolacao);

            StatusCondicionante status = statusCondicionanteRepository.findById(Integer.parseInt(payload.get("statusId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Status não encontrado"));
            condicionante.setStatusCondicionante(status);

            RespTerceiros respTerceiros = respTerceirosRepository.findById(Integer.parseInt(payload.get("respTerceirosId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Responsável terceiros não encontrado"));
            condicionante.setResponsabilidadeTerceiros(respTerceiros);

            RespCliente respCliente = respClienteRepository.findById(Integer.parseInt(payload.get("respClienteId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Responsável cliente não encontrado"));
            condicionante.setResponsabilidadeCliente(respCliente);

            RespZago respZago = respZagoRepository.findById(Integer.parseInt(payload.get("respZagoId").toString()))
                    .orElseThrow(() -> new IllegalArgumentException("Responsável Zago não encontrado"));
            condicionante.setResponsabilidadeZago(respZago);

            // Campos opcionais com valores padrão no banco
            if (payload.get("comprovacao") != null) {
                condicionante.setComprovacao(payload.get("comprovacao").toString());
            }
            if (payload.get("prazoVencimento") != null) {
                condicionante.setPrazoVencimento(payload.get("prazoVencimento").toString());
            }
            if (payload.get("acaoAtendimento") != null) {
                condicionante.setAcaoAtendimento(payload.get("acaoAtendimento").toString());
            }
            if (payload.get("situacao") != null) {
                condicionante.setSituacao(payload.get("situacao").toString());
            }
            if (payload.get("dataAtendimento") != null) {
                condicionante.setDataAtendimento(LocalDate.parse(payload.get("dataAtendimento").toString()));
            }

            // Salvar no banco
            Condicionante condicionanteSalva = condicionanteRepository.save(condicionante);

            // Registrar log
            String action = "Criação de Condicionante: ID " + condicionanteSalva.getCondicionanteId();
            logService.registrarLog(action, authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Condicionante criada com sucesso");
            response.put("condicionanteId", condicionanteSalva.getCondicionanteId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar condicionante: " + e.getMessage());
        }
    }




    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> atualizarCondicionante(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload,
            Authentication authentication) {
        try {
            // Validação de campos obrigatórios
            List<String> missingFields = new ArrayList<>();
            if (payload.get("obraId") == null) missingFields.add("obraId");
            if (payload.get("condicionante") == null) missingFields.add("condicionante");
            if (payload.get("identificacao") == null) missingFields.add("identificacao");
            if (payload.get("statusId") == null) missingFields.add("statusId");

            // Retorna erro se houver campos obrigatórios ausentes
            if (!missingFields.isEmpty()) {
                return ResponseEntity.badRequest().body("Campos obrigatórios ausentes: " + String.join(", ", missingFields));
            }

            // Busca a condicionante existente
            Condicionante condicionanteExistente = condicionanteService.buscarPorId(id);

            // Atualiza os campos necessários
            condicionanteExistente.setObra(
                    obraRepository.findById(Integer.parseInt(payload.get("obraId").toString()))
                            .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada"))
            );
            condicionanteExistente.setCondicionante(payload.get("condicionante").toString());
            condicionanteExistente.setIdentificacao(payload.get("identificacao").toString());
            condicionanteExistente.setStatusCondicionante(
                    statusCondicionanteRepository.findById(Integer.parseInt(payload.get("statusId").toString()))
                            .orElseThrow(() -> new IllegalArgumentException("Status não encontrado"))
            );

            // Atualiza outros campos opcionais
            if (payload.get("comprovacao") != null) {
                condicionanteExistente.setComprovacao(payload.get("comprovacao").toString());
            }
            if (payload.get("prazoVencimento") != null) {
                condicionanteExistente.setPrazoVencimento(payload.get("prazoVencimento").toString());
            }
            if (payload.get("acaoAtendimento") != null) {
                condicionanteExistente.setAcaoAtendimento(payload.get("acaoAtendimento").toString());
            }
            if (payload.get("situacao") != null) {
                condicionanteExistente.setSituacao(payload.get("situacao").toString());
            }
            if (payload.get("dataAtendimento") != null) {
                condicionanteExistente.setDataAtendimento(LocalDate.parse(payload.get("dataAtendimento").toString()));
            }

            // Salva a atualização
            condicionanteService.salvarCondicionante(condicionanteExistente);


            // Registrar log
            String action = "Atualização de Condicionante: ID " + id;
            logService.registrarLog(action, authentication);

            return ResponseEntity.ok("Condicionante atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar condicionante: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> excluirCondicionante(@PathVariable int id) {
        try {
            // Verifica e remove a condicionante diretamente
            condicionanteService.excluirCondicionante(id);
            return ResponseEntity.ok("Condicionante excluída com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condicionante não encontrada: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir condicionante: " + e.getMessage());
        }
    }

}

