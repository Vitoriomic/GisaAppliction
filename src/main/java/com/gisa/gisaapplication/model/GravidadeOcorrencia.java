package com.gisa.gisaapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gravidade_ocorrencia")
public class GravidadeOcorrencia {

    @Id
    @Column(name = "gravidadeid")
    private Integer gravidadeId;

    @Column(name = "gravidade", nullable = false, length = 50)
    private String gravidade;

    // Getters e setters
    public Integer getGravidadeId() {
        return gravidadeId;
    }

    public void setGravidadeId(Integer gravidadeId) {
        this.gravidadeId = gravidadeId;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }
}
