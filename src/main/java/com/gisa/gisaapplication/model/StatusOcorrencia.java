package com.gisa.gisaapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status_ocorrencia")
public class StatusOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusocorrenciaid")
    private Integer id;

    @Column(name = "statusocorrencia", nullable = false, length = 50)
    private String descricao;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
