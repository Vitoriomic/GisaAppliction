package com.gisa.gisaapplication.controller;

import com.gisa.gisaapplication.model.RespCliente;
import com.gisa.gisaapplication.model.RespTerceiros;
import com.gisa.gisaapplication.model.RespZago;
import com.gisa.gisaapplication.service.RespClienteService;
import com.gisa.gisaapplication.service.RespTerceirosService;
import com.gisa.gisaapplication.service.RespZagoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/responsabilidades")
public class ResponsabilidadeController{

    private final RespTerceirosService respTerceirosService;
    private final RespClienteService respClienteService;
    private final RespZagoService respZagoService;

    public ResponsabilidadeController(RespTerceirosService respTerceirosService, RespClienteService respClienteService, RespZagoService respZagoService) {
        this.respTerceirosService = respTerceirosService;
        this.respClienteService = respClienteService;
        this.respZagoService = respZagoService;
    }

    @GetMapping("/terceiros")
    public List<RespTerceiros> listarRespTerceiros() {
        return respTerceirosService.listarTodos();
    }

    @GetMapping("/cliente")
    public List<RespCliente> listarRespCliente() {
        return respClienteService.listarTodos();
    }

    @GetMapping("/zago")
    public List<RespZago> listarRespZago() {
        return respZagoService.listarTodos();
    }
}
