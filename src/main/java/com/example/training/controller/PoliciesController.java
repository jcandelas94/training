package com.example.training.controller;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.dto.PolicyDto;
import com.example.training.service.AccidentsService;
import com.example.training.service.PoliciesService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PoliciesController {

    private final PoliciesService policyService;
    private final AccidentsService accidentsService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;


    public PoliciesController(PoliciesService policiesService, AccidentsService accidentsService) {
        this.policyService = policiesService;
        this.accidentsService = accidentsService;
    }

    @GetMapping
    public List<PolicyDto> getPolicies() {
        String userId = getUserIdFromContext();
        return policyService.getPolicies(userId);
    }

    @GetMapping("/{policyNumber}")
    public PolicyDto getPolicyById(@PathVariable String policyNumber) {
        return policyService.getPolicyById(policyNumber);
    }

    @GetMapping("/{policyNumber}/accidents")
    public List<AccidentDto> getAccidentsByPolicy(@PathVariable String policyNumber) {
        return accidentsService.getAccidentsByPolicy(policyNumber);
    }

    @GetMapping("/{policyNumber}/accidents/{accidentId}")
    public AccidentDto getAccidentById(@PathVariable String policyNumber, @PathVariable String accidentId) {
        return accidentsService.getAccidentById(accidentId);
    }

    private String getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/circuit-breaker/status")
    public String getCircuitBreakerStatus() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("accidents");
        return "Circuit breaker status: " + circuitBreaker.getState().name();
    }

}
