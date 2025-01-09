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

    // Salvar ou atualizar obra
    public void salvarObra(Obra obra) {
        obraRepository.save(obra);
    }

    // Excluir obra por ID
    public void excluirObra(Integer id) {
        Obra obra = buscarPorId(id);
        obraRepository.delete(obra);
    }

    // Buscar obras por nome
    public List<Obra> buscarPorNome(String nome) {
        return obraRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Buscar obras por status
    public List<Obra> buscarPorStatus(String status) {
        return obraRepository.findByStatus(status);
    }

    // Buscar obras por localidade
    public List<Obra> buscarPorLocalidade(String localidade) {
        return obraRepository.findByLocalidade(localidade);
    }
}
