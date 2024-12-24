package com.gisa.gisaapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String menuPrincipal() {
        return "redirect:/index.html";
    }
}