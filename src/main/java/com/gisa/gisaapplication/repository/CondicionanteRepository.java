package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.Condicionante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondicionanteRepository extends JpaRepository<Condicionante, Integer> {

    // Consulta para filtrar por obra
    @Query("SELECT c FROM Condicionante c WHERE c.obra.obraid = :obraId")
    List<Condicionante> findByObraId(@Param("obraId") Integer obraId);

    // Consulta para filtrar por status
    @Query("SELECT c FROM Condicionante c WHERE c.statusCondicionante.statusId = :statusId")
    List<Condicionante> findByStatusId(@Param("statusId") Integer statusId);

    // Consulta para filtrar por obra e status
    @Query("SELECT c FROM Condicionante c WHERE c.obra.obraid = :obraId AND c.statusCondicionante.statusId = :statusId")
    List<Condicionante> findByObraIdAndStatusId(@Param("obraId") Integer obraId, @Param("statusId") Integer statusId);
}
