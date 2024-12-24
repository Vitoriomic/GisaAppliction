package com.gisa.gisaapplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "condicionante")
public class Condicionante {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condicionanteid")
    private Integer condicionanteId;

    @Column(name = "condicionante", nullable = false, columnDefinition = "TEXT")
    private String condicionante;

    @Column(name = "identificacao", nullable = false, length = 50)
    private String identificacao;

    @Column(name = "comprovacao", nullable = false, columnDefinition = "TEXT DEFAULT 'Sem comprovação'")
    private String comprovacao;

    @Column(name = "prazovencimento", length = 200, columnDefinition = "VARCHAR(200) DEFAULT 'Não definido'")
    private String prazoVencimento;

    @Column(name = "acaoatendimento", columnDefinition = "TEXT DEFAULT 'Pendente'")
    private String acaoAtendimento;

    @Column(name = "situacao", columnDefinition = "TEXT DEFAULT 'Não definida'")
    private String situacao;

    @Column(name = "dataatendimento")
    private LocalDate dataAtendimento;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "obraid", nullable = false)
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "protocolacaoid", nullable = false)
    private Protocolacao protocolacao;

    @ManyToOne
    @JoinColumn(name = "statusid", nullable = false)
    private StatusCondicionante statusCondicionante;

    @ManyToOne
    @JoinColumn(name = "respterceirosid", nullable = false)
    private RespTerceiros responsabilidadeTerceiros;

    @ManyToOne
    @JoinColumn(name = "respclienteid", nullable = false)
    private RespCliente responsabilidadeCliente;

    @ManyToOne
    @JoinColumn(name = "respzagoid", nullable = false)
    private RespZago responsabilidadeZago;

    // Getters e Setters
    public Integer getCondicionanteId() {
        return condicionanteId;
    }

    public void setCondicionanteId(Integer condicionanteId) {
        this.condicionanteId = condicionanteId;
    }

    public String getCondicionante() {
        return condicionante;
    }

    public void setCondicionante(String condicionante) {
        this.condicionante = condicionante;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getComprovacao() {
        return comprovacao;
    }

    public void setComprovacao(String comprovacao) {
        this.comprovacao = comprovacao;
    }

    public String getPrazoVencimento() {
        return prazoVencimento;
    }

    public void setPrazoVencimento(String prazoVencimento) {
        this.prazoVencimento = prazoVencimento;
    }

    public String getAcaoAtendimento() {
        return acaoAtendimento;
    }

    public void setAcaoAtendimento(String acaoAtendimento) {
        this.acaoAtendimento = acaoAtendimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Protocolacao getProtocolacao() {
        return protocolacao;
    }

    public void setProtocolacao(Protocolacao protocolacao) {
        this.protocolacao = protocolacao;
    }

    public StatusCondicionante getStatusCondicionante() {
        return statusCondicionante;
    }

    public void setStatusCondicionante(StatusCondicionante statusCondicionante) {
        this.statusCondicionante = statusCondicionante;
    }

    public RespTerceiros getResponsabilidadeTerceiros() {
        return responsabilidadeTerceiros;
    }

    public void setResponsabilidadeTerceiros(RespTerceiros responsabilidadeTerceiros) {
        this.responsabilidadeTerceiros = responsabilidadeTerceiros;
    }

    public RespCliente getResponsabilidadeCliente() {
        return responsabilidadeCliente;
    }

    public void setResponsabilidadeCliente(RespCliente responsabilidadeCliente) {
        this.responsabilidadeCliente = responsabilidadeCliente;
    }

    public RespZago getResponsabilidadeZago() {
        return responsabilidadeZago;
    }

    public void setResponsabilidadeZago(RespZago responsabilidadeZago) {
        this.responsabilidadeZago = responsabilidadeZago;
    }
}