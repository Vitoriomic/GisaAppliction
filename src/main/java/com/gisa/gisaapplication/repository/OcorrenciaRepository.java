package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

    @Query("SELECT o FROM Ocorrencia o WHERE o.obra.obraid = :obraId")
    List<Ocorrencia> findByObraId(@Param("obraId") Integer obraId);

    @Query("SELECT o FROM Ocorrencia o WHERE o.statusOcorrencia.id = :statusId")
    List<Ocorrencia> findByStatusId(@Param("statusId") Integer statusId);

    @Query("SELECT o FROM Ocorrencia o WHERE o.obra.obraid = :obraId AND o.statusOcorrencia.id = :statusId")
    List<Ocorrencia> findByObraIdAndStatusId(@Param("obraId") Integer obraId, @Param("statusId") Integer statusId);
}

