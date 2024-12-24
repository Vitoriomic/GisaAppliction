package com.gisa.gisaapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status_obra")
public class StatusObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusobraid")
    private Integer statusObraId;

    @Column(nullable = false)
    private String status;

    // Getters e Setters
    public Integer getStatusObraId() {
        return statusObraId;
    }

    public void setStatusObraId(Integer statusObraId) {
        this.statusObraId = statusObraId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
