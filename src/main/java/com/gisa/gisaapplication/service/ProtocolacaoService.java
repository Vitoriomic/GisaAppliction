package com.gisa.gisaapplication.service;

import com.gisa.gisaapplication.model.Protocolacao;
import com.gisa.gisaapplication.repository.ProtocolacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProtocolacaoService {

    private final ProtocolacaoRepository protocolacaoRepository;

    public ProtocolacaoService(ProtocolacaoRepository protocolacaoRepository) {
        this.protocolacaoRepository = protocolacaoRepository;
    }

    public Protocolacao buscarPorId(Integer id) {
        return protocolacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Protocolacao não encontrada com ID: " + id));
    }
}
