package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Condicionante;
import com.gisa.gisaapplication.repository.CondicionanteRepository;
import org.springframework.stereotype.Service;

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
    public List<Condicionante> buscarComFiltros(Integer obraId, Integer statusId) {
        if (obraId != null && statusId != null) {
            return condicionanteRepository.findByObraIdAndStatusId(obraId, statusId);
        } else if (obraId != null) {
            return condicionanteRepository.findByObraId(obraId);
        } else if (statusId != null) {
            return condicionanteRepository.findByStatusId(statusId);
        }
        return condicionanteRepository.findAll();
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
}