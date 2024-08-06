package com.example.training.controller;

import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.service.PoliciesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PoliciesController {

    private final PoliciesService polizaService;

    public PoliciesController(PoliciesService polizaService) {
        this.polizaService = polizaService;
    }

    // http://localhost:8080/api/policies?dni=666
    @GetMapping("/policies")
    public PolicyWrapperDto getPolicies(@RequestParam String dni) {
        return polizaService.getPolicies(dni);
    }

    // http://localhost:8080/api/policies/666
    @GetMapping("/policies/{policyNumber}")
    public PolicyWrapperDto getPolicyById(@PathVariable String policyNumber) {
        return polizaService.getPolicyById(policyNumber);
    }
}
