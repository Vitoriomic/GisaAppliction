package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "resp_cliente")
public class RespCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer respclienteid;

    @Column(nullable = false)
    private String responsabilidade;

    // Getters e Setters
    public Integer getRespclienteid() {
        return respclienteid;
    }

    public void setRespclienteid(Integer respclienteid) {
        this.respclienteid = respclienteid;
    }

    public String getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(String responsabilidade) {
        this.responsabilidade = responsabilidade;
    }
}
