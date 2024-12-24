package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.StatusCondicionante;
import com.gisa.gisaapplication.repository.StatusCondicionanteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/status-condicionantes")
public class StatusCondicionanteController {

    private final StatusCondicionanteRepository statusCondicionanteRepository;

    public StatusCondicionanteController(StatusCondicionanteRepository repository) {
        this.statusCondicionanteRepository = repository;
    }

    @GetMapping
    public List<StatusCondicionante> listarTodos() {
        return statusCondicionanteRepository.findAll();
    }
}
