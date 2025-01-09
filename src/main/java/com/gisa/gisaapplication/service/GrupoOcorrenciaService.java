package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.GrupoOcorrencia;
import com.gisa.gisaapplication.repository.GrupoOcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoOcorrenciaService {

    private final GrupoOcorrenciaRepository grupoOcorrenciaRepository;

    public GrupoOcorrenciaService(GrupoOcorrenciaRepository grupoOcorrenciaRepository) {
        this.grupoOcorrenciaRepository = grupoOcorrenciaRepository;
    }

    public List<GrupoOcorrencia> listarTodos() {
        return grupoOcorrenciaRepository.findAll();
    }

    public GrupoOcorrencia buscarPorId(Integer id) {
        return grupoOcorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de Ocorrência não encontrado com ID: " + id));
    }

    public void salvarGrupoOcorrencia(GrupoOcorrencia grupoOcorrencia) {
        grupoOcorrenciaRepository.save(grupoOcorrencia);
    }

    public void excluirGrupoOcorrencia(Integer id) {
        GrupoOcorrencia grupoOcorrencia = buscarPorId(id);
        grupoOcorrenciaRepository.delete(grupoOcorrencia);
    }
}
