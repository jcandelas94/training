package com.example.training.controller;

import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.service.PoliciesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PoliciesController {

    private final PoliciesService polizaService;

    public PoliciesController(PoliciesService polizaService) {
        this.polizaService = polizaService;
    }

    // http://localhost:8080/api/policies?dni=666
    @GetMapping("/policies")
    public PolicyWrapperDto obtenerPolizas(@RequestParam String dni) {
        return polizaService.listarPolizas(dni);
    }
}
