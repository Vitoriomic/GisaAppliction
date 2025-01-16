package com.gisa.gisaapplication.model;

import com.gisa.gisaapplication.auth.model.Log;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ocorrencia")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocorrenciaid")
    private Integer ocorrenciaId;

    @Transient // Evita persistência no banco
    private Log ultimoLog;

    @Column(name = "dataregistro", nullable = false)
    private LocalDate dataRegistro;

    @ManyToOne
    @JoinColumn(name = "obraid", nullable = false)
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "supervisorid", nullable = false)
    private Usuario supervisor;

    @ManyToOne
    @JoinColumn(name = "grupoocorrenciaid", nullable = false)
    private GrupoOcorrencia grupoOcorrencia;

    @Column(name = "ocorrencia", nullable = false, columnDefinition = "TEXT")
    private String ocorrencia;

    @Column(name = "ocorrenciadetalhada", columnDefinition = "TEXT")
    private String ocorrenciaDetalhada;

    @ManyToOne
    @JoinColumn(name = "gravidadeid", nullable = false)
    private GravidadeOcorrencia gravidade;

    @Column(name = "solucaoimediata", nullable = false, columnDefinition = "TEXT")
    private String solucaoImediata;

    @Column(name = "sugestaosoluaodefinitiva", columnDefinition = "TEXT")
    private String sugestaoSolucaoDefinitiva;

    @Column(name = "dataacordada")
    private LocalDate dataAcordada;

    @Column(name = "x", nullable = false, length = 100)
    private String x;

    @Column(name = "y", nullable = false, length = 100)
    private String y;

    @ManyToOne
    @JoinColumn(name = "statusocorrenciaid", nullable = false)
    private StatusOcorrencia statusOcorrencia;

    @Column(name = "tratamentoocorrencia", columnDefinition = "TEXT DEFAULT 'Não definida'")
    private String tratamentoOcorrencia;

    @Column(name = "dataresolucao")
    private LocalDate dataResolucao;

    @Column(name = "evidencia", nullable = false, columnDefinition = "TEXT DEFAULT 'Em espera'")
    private String evidencia;

    @Column(name = "outro_grupo_ocorrencia", columnDefinition = "TEXT")
    private String outroGrupoOcorrencia;

    // Getters e setters
    public Integer getOcorrenciaId() {
        return ocorrenciaId;
    }

    public void setOcorrenciaId(Integer ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
    }

    public Log getUltimoLog() {
        return ultimoLog;
    }

    public void setUltimoLog(Log ultimoLog) {
        this.ultimoLog = ultimoLog;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Usuario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Usuario supervisor) {
        this.supervisor = supervisor;
    }

    public GrupoOcorrencia getGrupoOcorrencia() {
        return grupoOcorrencia;
    }

    public void setGrupoOcorrencia(GrupoOcorrencia grupoOcorrencia) {
        this.grupoOcorrencia = grupoOcorrencia;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getOcorrenciaDetalhada() {
        return ocorrenciaDetalhada;
    }

    public void setOcorrenciaDetalhada(String ocorrenciaDetalhada) {
        this.ocorrenciaDetalhada = ocorrenciaDetalhada;
    }

    public GravidadeOcorrencia getGravidade() {
        return gravidade;
    }

    public void setGravidade(GravidadeOcorrencia gravidade) {
        this.gravidade = gravidade;
    }

    public String getSolucaoImediata() {
        return solucaoImediata;
    }

    public void setSolucaoImediata(String solucaoImediata) {
        this.solucaoImediata = solucaoImediata;
    }

    public String getSugestaoSolucaoDefinitiva() {
        return sugestaoSolucaoDefinitiva;
    }

    public void setSugestaoSolucaoDefinitiva(String sugestaoSolucaoDefinitiva) {
        this.sugestaoSolucaoDefinitiva = sugestaoSolucaoDefinitiva;
    }

    public LocalDate getDataAcordada() {
        return dataAcordada;
    }

    public void setDataAcordada(LocalDate dataAcordada) {
        this.dataAcordada = dataAcordada;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public StatusOcorrencia getStatusOcorrencia() {
        return statusOcorrencia;
    }

    public void setStatusOcorrencia(StatusOcorrencia statusOcorrencia) {
        this.statusOcorrencia = statusOcorrencia;
    }

    public String getTratamentoOcorrencia() {
        return tratamentoOcorrencia;
    }

    public void setTratamentoOcorrencia(String tratamentoOcorrencia) {
        this.tratamentoOcorrencia = tratamentoOcorrencia;
    }

    public LocalDate getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDate dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getOutroGrupoOcorrencia() {
        return outroGrupoOcorrencia;
    }

    public void setOutroGrupoOcorrencia(String outroGrupoOcorrencia) {
        this.outroGrupoOcorrencia = outroGrupoOcorrencia;
    }
}
