package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.RespCliente;
import com.gisa.gisaapplication.repository.RespClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespClienteService {
    private final RespClienteRepository repository;

    public RespClienteService(RespClienteRepository repository) {
        this.repository = repository;
    }

    public List<RespCliente> listarTodos() {
        return repository.findAll();
    }
}
