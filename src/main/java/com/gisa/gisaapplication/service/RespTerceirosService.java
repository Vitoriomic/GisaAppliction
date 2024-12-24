package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.RespTerceiros;
import com.gisa.gisaapplication.repository.RespTerceirosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespTerceirosService {
    private final RespTerceirosRepository repository;

    public RespTerceirosService(RespTerceirosRepository repository) {
        this.repository = repository;
    }

    public List<RespTerceiros> listarTodos() {
        return repository.findAll();
    }
}
