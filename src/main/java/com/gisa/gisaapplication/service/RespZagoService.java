package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.RespZago;
import com.gisa.gisaapplication.repository.RespZagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespZagoService {
    private final RespZagoRepository repository;

    public RespZagoService(RespZagoRepository repository) {
        this.repository = repository;
    }

    public List<RespZago> listarTodos() {
        return repository.findAll();
    }
    public RespZago buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RespZago não encontrado com ID: " + id));
    }
}
