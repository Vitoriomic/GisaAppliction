package com.gisa.gisaapplication.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("/index")
    public String indexPage() {
        return "index"; // Refere-se ao arquivo templates/index.html
    }

    @GetMapping("/condicionantes")
    public String condicionantesPage() {
        return "condicionantes"; // Refere-se ao arquivo templates/condicionantes.html
    }

    @GetMapping("/ocorrencias")
    public String ocorrenciasPage() {
        return "ocorrencias"; // Refere-se ao arquivo templates/ocorrencias.html
    }
}
