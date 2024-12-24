package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Obra;
import com.gisa.gisaapplication.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    // Listar todas as obras
    public List<Obra> listarTodas() {
        return obraRepository.findAll();
    }

    // Buscar obra por ID
    public Obra buscarPorId(Integer id) {
        return obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com ID: " + id));
    }
}
