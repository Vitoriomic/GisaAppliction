package com.gisa.gisaapplication.model;
import jakarta.persistence.*;

@Entity
@Table(name = "status_condicionante")
public class StatusCondicionante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusid")
    private Integer statusId;

    @Column(nullable = false)
    private String status;

    // Getters e Setters
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
