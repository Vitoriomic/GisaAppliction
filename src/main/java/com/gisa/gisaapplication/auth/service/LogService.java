package com.gisa.gisaapplication.auth.service;

import com.gisa.gisaapplication.auth.model.Log;
import com.gisa.gisaapplication.auth.model.User;
import com.gisa.gisaapplication.auth.repository.LogRepository;
import com.gisa.gisaapplication.auth.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    private final LogRepository LogRepository;
    private final UserRepository UserRepository;

    public LogService(LogRepository logRepository, UserRepository userRepository) {
        this.LogRepository = logRepository;
        this.UserRepository = userRepository;
    }

    public void registrarLog(String action, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("Ação não permitida sem autenticação válida.");
        }

        // Buscar usuário na tabela `users`
        User user = UserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado no sistema."));

        // Criar e salvar o log
        Log log = new Log();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setPerformedBy(user.getUsername()); // Garantir que é o usuário autenticado
        LogRepository.save(log);
    }
}
