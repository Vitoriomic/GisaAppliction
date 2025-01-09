package com.gisa.gisaapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permissoes")
public class Permissoes {

    @Id
    @Column(name = "permissoesid")
    private Integer permissoesId;

    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel;

    // Getters e setters
    public Integer getPermissoesId() {
        return permissoesId;
    }

    public void setPermissoesId(Integer permissoesId) {
        this.permissoesId = permissoesId;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
