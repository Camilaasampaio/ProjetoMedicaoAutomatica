package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class StatusController {

    @GetMapping
    public String healthCheck() {
        return "O serviço está UP!";
    }
}
