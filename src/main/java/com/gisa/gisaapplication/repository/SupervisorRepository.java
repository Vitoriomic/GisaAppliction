package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Repositório para Supervisor
public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {

    // Busca supervisores por obra usando o ID da obra
    @Query("SELECT s FROM Supervisor s WHERE s.obra.obraid = :obraId")
    List<Supervisor> findByObraId(@Param("obraId") Integer obraId);
}
