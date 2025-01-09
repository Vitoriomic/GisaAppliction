package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Supervisor;
import com.gisa.gisaapplication.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    public List<Supervisor> listarTodos() {
        return supervisorRepository.findAll();
    }

    public Supervisor buscarPorId(Integer id) {
        return supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado com ID: " + id));
    }

    public void salvarSupervisor(Supervisor supervisor) {
        supervisorRepository.save(supervisor);
    }

    public void excluirSupervisor(Integer id) {
        Supervisor supervisor = buscarPorId(id);
        supervisorRepository.delete(supervisor);
    }
}
