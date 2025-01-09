package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.GrupoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositório para Grupo de Ocorrência
public interface GrupoOcorrenciaRepository extends JpaRepository<GrupoOcorrencia, Integer> {

    // Busca todos os grupos de ocorrência
    List<GrupoOcorrencia> findAll();
}
