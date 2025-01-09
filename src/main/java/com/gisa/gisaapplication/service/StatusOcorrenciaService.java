package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.StatusOcorrencia;
import com.gisa.gisaapplication.repository.StatusOcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusOcorrenciaService {

    private final StatusOcorrenciaRepository statusOcorrenciaRepository;

    public StatusOcorrenciaService(StatusOcorrenciaRepository statusOcorrenciaRepository) {
        this.statusOcorrenciaRepository = statusOcorrenciaRepository;
    }

    public List<StatusOcorrencia> listarTodos() {
        return statusOcorrenciaRepository.findAll();
    }

    public StatusOcorrencia buscarPorId(Integer id) {
        return statusOcorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status de Ocorrência não encontrado com ID: " + id));
    }

    public void salvarStatusOcorrencia(StatusOcorrencia statusOcorrencia) {
        statusOcorrenciaRepository.save(statusOcorrencia);
    }

    public void excluirStatusOcorrencia(Integer id) {
        StatusOcorrencia statusOcorrencia = buscarPorId(id);
        statusOcorrenciaRepository.delete(statusOcorrencia);
    }
}
