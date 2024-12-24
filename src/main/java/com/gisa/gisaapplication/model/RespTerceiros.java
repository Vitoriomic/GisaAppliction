package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "resp_terceiros")
public class RespTerceiros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer respterceirosid;

    @Column(nullable = false)
    private String responsabilidade;

    // Getters e Setters
    public Integer getRespterceirosid() {
        return respterceirosid;
    }

    public void setRespterceirosid(Integer respterceirosid) {
        this.respterceirosid = respterceirosid;
    }

    public String getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(String responsabilidade) {
        this.responsabilidade = responsabilidade;
    }
}
