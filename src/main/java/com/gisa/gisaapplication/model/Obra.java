package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "obra")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obraid")
    private Integer obraid;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String localidade;

    @ManyToOne
    @JoinColumn(name = "statusobraid", nullable = false)
    private StatusObra statusObra;

    @ManyToOne
    @JoinColumn(name = "contratoid", nullable = false)
    private Contrato contrato;

    // Getters e Setters
    public Integer getObraid() {
        return obraid;
    }

    public void setObraid(Integer obraid) {
        this.obraid = obraid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public StatusObra getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(StatusObra statusObra) {
        this.statusObra = statusObra;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
