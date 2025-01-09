package com.gisa.gisaapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status_usuario")
public class StatusUsuario {

    @Id
    @Column(name = "statususuarioid")
    private Integer statusUsuarioId;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    // Getters e setters
    public Integer getStatusUsuarioId() {
        return statusUsuarioId;
    }

    public void setStatusUsuarioId(Integer statusUsuarioId) {
        this.statusUsuarioId = statusUsuarioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
