package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "protocolacao")
public class Protocolacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protocolacaoid")
    private Integer protocolacaoId;

    @Column(nullable = false)
    private String status;

    // Construtor padrão (necessário para o JPA)
    public Protocolacao() {}

    // Construtor que aceita um Integer para deserialização
    public Protocolacao(Integer protocolacaoId) {
        this.protocolacaoId = protocolacaoId;
    }

    // Getters e Setters
    public Integer getProtocolacaoId() {
        return protocolacaoId;
    }

    public void setProtocolacaoId(Integer protocolacaoId) {
        this.protocolacaoId = protocolacaoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
