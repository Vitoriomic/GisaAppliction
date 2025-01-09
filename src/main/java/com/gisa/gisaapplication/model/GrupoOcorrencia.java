package com.gisa.gisaapplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grupo_ocorrencia")
public class GrupoOcorrencia {

    @Id
    @Column(name = "grupoocorrenciaid")
    private Integer grupoOcorrenciaId;

    @Column(name = "grupoocorrencia", nullable = false, length = 50)
    private String grupoOcorrencia;

    // Getters e setters
    public Integer getGrupoOcorrenciaId() {
        return grupoOcorrenciaId;
    }

    public void setGrupoOcorrenciaId(Integer grupoOcorrenciaId) {
        this.grupoOcorrenciaId = grupoOcorrenciaId;
    }

    public String getGrupoOcorrencia() {
        return grupoOcorrencia;
    }

    public void setGrupoOcorrencia(String grupoOcorrencia) {
        this.grupoOcorrencia = grupoOcorrencia;
    }
}
