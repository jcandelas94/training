package com.example.training.controller;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.dto.PolicyConditionsDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.service.AccidentsService;
import com.example.training.service.PoliciesService;
import org.springframework.cache.annotation.Cacheable;
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

    // http://localhost:8080/policies
//    @Cacheable(value = "policiesCache", key = "#userId")
    @GetMapping
    public PolicyWrapperDto getPolicies() {
        String userId = getUserIdFromContext();
        return policyService.getPolicies(userId);
    }

    // http://localhost:8080/policies/666
//    @Cacheable(value = "policyCache", key = "#policyNumber")
    @GetMapping("/{policyNumber}")
    public PolicyWrapperDto getPolicyById(@PathVariable String policyNumber) {
        return policyService.getPolicyById(policyNumber);
    }

    // http://localhost:8080/policies/666/conditions
//    @Cacheable(value = "policyConditionsCache", key = "#policyNumber")
    @GetMapping("/{policyNumber}/conditions")
    public List<PolicyConditionsDto> getPolicyConditions(@PathVariable String policyNumber) {
        return policyService.getPolicyConditions(policyNumber);
    }

    // http://localhost:8080/policies/666/accidents
//    @Cacheable(value = "accidentsCache", key = "#policyNumber")
    @GetMapping("/{policyNumber}/accidents")
    public List<AccidentDto> getAccidentsByPolicy(@PathVariable String policyNumber) {
        return accidentsService.getAccidentsByPolicy(policyNumber);
    }

//    @Cacheable(value = "accidentCache", key = "#accidentId")
    @GetMapping("/accidents/{accidentId}")
    public AccidentDto getAccidentById(@PathVariable String accidentId) {
        return accidentsService.getAccidentById(accidentId);
    }

    // http://localhost:8080/policies/accidents/666
    private String getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
