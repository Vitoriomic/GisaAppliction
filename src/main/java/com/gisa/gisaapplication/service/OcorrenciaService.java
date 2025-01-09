package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Ocorrencia;
import com.gisa.gisaapplication.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    // Listar todas as ocorrências
    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    // Buscar ocorrências por ID
    public Ocorrencia buscarPorId(Integer id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com ID: " + id));
    }

    // Buscar ocorrências com filtros
    public List<Ocorrencia> buscarComFiltros(Integer obraId, Integer statusId) {
        if (obraId != null && statusId != null) {
            return ocorrenciaRepository.findByObraIdAndStatusId(obraId, statusId);
        } else if (obraId != null) {
            return ocorrenciaRepository.findByObraId(obraId);
        } else if (statusId != null) {
            return ocorrenciaRepository.findByStatusId(statusId);
        }
        return ocorrenciaRepository.findAll();
    }

    // Salvar nova ocorrência
    public Ocorrencia salvarOcorrencia(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    // Atualizar ocorrência existente
    public Ocorrencia atualizarOcorrencia(Integer id, Ocorrencia ocorrenciaAtualizada) {
        Ocorrencia ocorrenciaExistente = buscarPorId(id);
        // Atualizar campos necessários
        ocorrenciaExistente.setDataRegistro(ocorrenciaAtualizada.getDataRegistro());
        ocorrenciaExistente.setObra(ocorrenciaAtualizada.getObra());
        ocorrenciaExistente.setSupervisor(ocorrenciaAtualizada.getSupervisor());
        ocorrenciaExistente.setGrupoOcorrencia(ocorrenciaAtualizada.getGrupoOcorrencia());
        ocorrenciaExistente.setOcorrencia(ocorrenciaAtualizada.getOcorrencia());
        ocorrenciaExistente.setOcorrenciaDetalhada(ocorrenciaAtualizada.getOcorrenciaDetalhada());
        ocorrenciaExistente.setGravidade(ocorrenciaAtualizada.getGravidade());
        ocorrenciaExistente.setSolucaoImediata(ocorrenciaAtualizada.getSolucaoImediata());
        ocorrenciaExistente.setSugestaoSolucaoDefinitiva(ocorrenciaAtualizada.getSugestaoSolucaoDefinitiva());
        ocorrenciaExistente.setDataAcordada(ocorrenciaAtualizada.getDataAcordada());
        ocorrenciaExistente.setX(ocorrenciaAtualizada.getX());
        ocorrenciaExistente.setY(ocorrenciaAtualizada.getY());
        ocorrenciaExistente.setStatusOcorrencia(ocorrenciaAtualizada.getStatusOcorrencia());
        ocorrenciaExistente.setTratamentoOcorrencia(ocorrenciaAtualizada.getTratamentoOcorrencia());
        ocorrenciaExistente.setDataResolucao(ocorrenciaAtualizada.getDataResolucao());
        ocorrenciaExistente.setEvidencia(ocorrenciaAtualizada.getEvidencia());
        ocorrenciaExistente.setOutroGrupoOcorrencia(ocorrenciaAtualizada.getOutroGrupoOcorrencia());
        return ocorrenciaRepository.save(ocorrenciaExistente);
    }

    // Excluir ocorrência por ID
    public void excluirOcorrencia(Integer id) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }
}
