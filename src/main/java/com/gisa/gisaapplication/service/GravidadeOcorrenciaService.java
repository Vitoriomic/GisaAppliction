package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.GravidadeOcorrencia;
import com.gisa.gisaapplication.repository.GravidadeOcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GravidadeOcorrenciaService {

    @Autowired
    private GravidadeOcorrenciaRepository repository;

    public List<GravidadeOcorrencia> findAll() {
        return repository.findAll();
    }

    public Optional<GravidadeOcorrencia> findById(Integer id) {
        return repository.findById(id);
    }

    public GravidadeOcorrencia save(GravidadeOcorrencia gravidadeOcorrencia) {
        return repository.save(gravidadeOcorrencia);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
