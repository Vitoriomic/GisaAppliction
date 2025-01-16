package com.gisa.gisaapplication.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = ""; // Sua senha aqui
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}