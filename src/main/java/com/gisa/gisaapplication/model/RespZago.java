package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "resp_zago")
public class RespZago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer respzagoid;

    @Column(nullable = false)
    private String responsabilidade;

    // Getters e Setters
    public Integer getRespzagoid() {
        return respzagoid;
    }

    public void setRespzagoid(Integer respzagoid) {
        this.respzagoid = respzagoid;
    }

    public String getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(String responsabilidade) {
        this.responsabilidade = responsabilidade;
    }
}
