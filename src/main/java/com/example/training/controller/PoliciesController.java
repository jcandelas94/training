package com.example.training.controller;

import com.example.training.model.dto.PolicyConditionsDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.service.PoliciesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PoliciesController {

    private final PoliciesService polizaService;

    public PoliciesController(PoliciesService polizaService) {
        this.polizaService = polizaService;
    }

    // http://localhost:8080/policies?dni=666
    @GetMapping
    public PolicyWrapperDto getPolicies(@RequestParam String dni) {
        return polizaService.getPolicies(dni);
    }

    // http://localhost:8080/policies/666
    @GetMapping("/{policyNumber}")
    public PolicyWrapperDto getPolicyById(@PathVariable String policyNumber) {
        return polizaService.getPolicyById(policyNumber);
    }

    // http://localhost:8080/policies/666/conditions
    @GetMapping("/{policyNumber}/conditions")
    public List<PolicyConditionsDto> getPolicyConditions(@PathVariable String policyNumber) {
        return polizaService.getPolicyConditions(policyNumber);
    }
}
