package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.StatusOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositório para Status de Ocorrência
public interface StatusOcorrenciaRepository extends JpaRepository<StatusOcorrencia, Integer> {

    // Busca todos os status de ocorrência
    List<StatusOcorrencia> findAll();
}
