package com.gisa.gisaapplication.repository;

import com.gisa.gisaapplication.model.Ocorrencia;
import com.gisa.gisaapplication.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObraRepository extends JpaRepository<Obra, Integer> {

    // Buscar por nome da obra
    List<Obra> findByNomeContainingIgnoreCase(String nome);

    // Buscar por status da obra
    @Query("SELECT o FROM Obra o WHERE o.statusObra.status = :status")
    List<Obra> findByStatus(@Param("status") String status);

    // Buscar por localidade
    @Query("SELECT o FROM Obra o WHERE o.localidade LIKE %:localidade%")
    List<Obra> findByLocalidade(@Param("localidade") String localidade);
}

