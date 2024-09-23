package com.example.training.controller;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.dto.PolicyDto;
import com.example.training.service.AccidentsService;
import com.example.training.service.PoliciesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PoliciesController {

    private final PoliciesService policyService;
    private final AccidentsService accidentsService;


    public PoliciesController(PoliciesService policiesService, AccidentsService accidentsService) {
        this.policyService = policiesService;
        this.accidentsService = accidentsService;
    }

    @GetMapping
    public List<PolicyDto> getPolicies(Authentication authentication) {
        return policyService.getPolicies(authentication.getName());
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
}
