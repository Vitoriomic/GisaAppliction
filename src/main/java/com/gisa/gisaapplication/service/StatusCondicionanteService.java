package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.StatusCondicionante;
import com.gisa.gisaapplication.repository.StatusCondicionanteRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusCondicionanteService {

    private final StatusCondicionanteRepository statusCondicionanteRepository;

    public StatusCondicionanteService(StatusCondicionanteRepository statusCondicionanteRepository) {
        this.statusCondicionanteRepository = statusCondicionanteRepository;
    }

    public StatusCondicionante buscarPorId(Integer id) {
        return statusCondicionanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StatusCondicionante não encontrado com ID: " + id));
    }
}
