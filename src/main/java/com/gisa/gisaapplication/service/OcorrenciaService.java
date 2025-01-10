package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Ocorrencia;
import com.gisa.gisaapplication.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    // Processar uma única ocorrência para calcular prazos
    private Ocorrencia processarOcorrencia(Ocorrencia ocorrencia) {
        LocalDate hoje = LocalDate.now();
        String tratamento = ocorrencia.getTratamentoOcorrencia(); // Valor inicial do banco

        if (!"Finalizada".equals(ocorrencia.getStatusOcorrencia().getDescricao())) {
            // Para status não finalizados, calcular prazo
            LocalDate dataAcordada = ocorrencia.getDataAcordada();
            if (dataAcordada != null) {
                if (hoje.isBefore(dataAcordada)) {
                    long diasRestantes = ChronoUnit.DAYS.between(hoje, dataAcordada);
                    tratamento = diasRestantes + " dias restantes";
                } else if (hoje.isEqual(dataAcordada)) {
                    tratamento = "Vence hoje";
                } else {
                    long diasVencidos = ChronoUnit.DAYS.between(dataAcordada, hoje);
                    tratamento = "Vencido há " + diasVencidos + " dias";
                }
            } else {
                tratamento = "Data não definida";
            }
        }

        // Atualiza o campo "tratamentoOcorrencia" com o valor calculado ou do banco
        ocorrencia.setTratamentoOcorrencia(tratamento);

        return ocorrencia;
    }



    // Processar lista de ocorrências
    private List<Ocorrencia> processarListaDeOcorrencias(List<Ocorrencia> ocorrencias) {
        return ocorrencias.stream()
                .map(this::processarOcorrencia)
                .collect(Collectors.toList());
    }

    // Listar todas as ocorrências com processamento
    public List<Ocorrencia> listarTodas() {
        return processarListaDeOcorrencias(ocorrenciaRepository.findAll());
    }

    // Buscar ocorrência por ID com processamento
    public Ocorrencia buscarPorId(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com ID: " + id));
        return processarOcorrencia(ocorrencia);
    }

    // Buscar ocorrências com filtros
    public List<Ocorrencia> buscarComFiltros(Integer obraId, Integer statusId, Integer grupoId, LocalDate data) {
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.findAll();

        if (obraId != null) {
            ocorrencias = ocorrencias.stream()
                    .filter(o -> o.getObra().getObraid().equals(obraId))
                    .collect(Collectors.toList());
        }

        if (statusId != null) {
            ocorrencias = ocorrencias.stream()
                    .filter(o -> o.getStatusOcorrencia().getId().equals(statusId))
                    .collect(Collectors.toList());
        }

        if (grupoId != null) {
            ocorrencias = ocorrencias.stream()
                    .filter(o -> o.getGrupoOcorrencia().getGrupoOcorrenciaId().equals(grupoId))
                    .collect(Collectors.toList());
        }

        if (data != null) {
            ocorrencias = ocorrencias.stream()
                    .filter(o -> o.getDataRegistro().isEqual(data))
                    .collect(Collectors.toList());
        }

        return processarListaDeOcorrencias(ocorrencias);
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
