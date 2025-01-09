package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.Supervisor;
import com.gisa.gisaapplication.service.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supervisores")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping
    public ResponseEntity<List<Supervisor>> listarTodos() {
        return ResponseEntity.ok(supervisorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supervisor> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(supervisorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Supervisor> salvarSupervisor(@RequestBody Supervisor supervisor) {
        supervisorService.salvarSupervisor(supervisor);
        return ResponseEntity.ok(supervisor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSupervisor(@PathVariable Integer id) {
        supervisorService.excluirSupervisor(id);
        return ResponseEntity.noContent().build();
    }
}
