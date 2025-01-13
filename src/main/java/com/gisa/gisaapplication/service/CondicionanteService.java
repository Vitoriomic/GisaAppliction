package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Condicionante;
import com.gisa.gisaapplication.repository.CondicionanteRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CondicionanteService {

    private final CondicionanteRepository condicionanteRepository;

    public CondicionanteService(CondicionanteRepository condicionanteRepository) {
        this.condicionanteRepository = condicionanteRepository;
    }

    // Buscar todos os condicionantes
    public List<Condicionante> listarTodos() {
        return condicionanteRepository.findAll();
    }

    // Buscar condicionante por ID
    public Condicionante buscarPorId(int id) {
        return condicionanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condicionante não encontrado com ID: " + id));
    }

    // Lógica para buscar condicionantes com filtros
    public List<Condicionante> buscarComFiltros(Integer obraId, String identificacao, Integer protocolacaoId,
                                                String acaoAtendimento, List<Integer> statusIds) {
        return condicionanteRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (obraId != null) {
                predicates.add(cb.equal(root.get("obra").get("obraid"), obraId));
            }
            if (identificacao != null && !identificacao.isEmpty()) {
                predicates.add(cb.like(root.get("identificacao"), "%" + identificacao + "%"));
            }
            if (protocolacaoId != null) {
                predicates.add(cb.equal(root.get("protocolacao").get("protocolacaoId"), protocolacaoId));
            }
            if ("Pendente".equalsIgnoreCase(acaoAtendimento)) {
                // Filtro para registros pendentes
                predicates.add(cb.equal(root.get("acaoAtendimento"), "Pendente"));
            } else if ("Preenchidas".equalsIgnoreCase(acaoAtendimento)) {
                // Filtro para registros preenchidos
                predicates.add(cb.or(
                        cb.notEqual(root.get("acaoAtendimento"), "Pendente"),
                        cb.isNull(root.get("acaoAtendimento"))
                ));
            }
            if (statusIds != null && !statusIds.isEmpty()) {
                predicates.add(root.get("statusCondicionante").get("statusId").in(statusIds));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }



    public void salvarCondicionante(Condicionante condicionante) {
        // Validações adicionais, se necessário
        if (condicionante.getObra() == null || condicionante.getCondicionante() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos.");
        }
        // Salvar no banco
        condicionanteRepository.save(condicionante);
    }
    // Excluir condicionante por ID
    public void excluirCondicionante(int id) {
        Condicionante condicionante = buscarPorId(id); // Verifica se a condicionante existe
        condicionanteRepository.delete(condicionante);
    }

    public List<Condicionante> buscarTodos() {
        // Busca todos os registros de condicionantes
        return condicionanteRepository.findAll();
    }
}