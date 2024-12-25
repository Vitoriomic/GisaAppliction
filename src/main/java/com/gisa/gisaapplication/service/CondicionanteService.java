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

    public Condicionante salvar(Condicionante condicionante) {
        condicionante.setCondicionanteId(null);
        return condicionanteRepository.save(condicionante);
    }
}
