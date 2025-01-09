package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.GravidadeOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GravidadeOcorrenciaRepository extends JpaRepository<GravidadeOcorrencia, Integer> {
}