package com.example.training.service;

import com.example.training.model.dto.PolicyDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.model.entity.Policy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoliciesService {

    private final RestTemplate restTemplate;
    private final String wiremockUrl1 = "http://localhost:8081";

    public PoliciesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PolicyWrapperDto listarPolizas(String dni) {
        String url = wiremockUrl1 + "/polizas?dni=" + dni;
        Policy[] policies = this.restTemplate.getForObject(url, Policy[].class);

        List<PolicyDto> policyDtos = mapToPolicyDto(policies);

        return PolicyWrapperDto.builder()
                .policies(policyDtos)
                .build();
    }

    private List<PolicyDto> mapToPolicyDto(Policy[] policies) {
        return policies != null ?
                List.of(policies).stream()
                        .map(policy -> new PolicyDto(policy.getPolizaId(), policy.getDescripcion()))
                        .collect(Collectors.toList()) :
                List.of();
    }


}
