package com.example.training.proxy;

import com.example.training.model.entity.Policy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PoliciesProxyService {

    private final RestTemplate restTemplate;
    private final String wiremockUrl1 = "http://localhost:8081";

    public PoliciesProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Policy[] getPolicies(String dni) {
        Policy[] policies;
        String url = wiremockUrl1 + "/polizas?dni=" + dni;
        return policies = this.restTemplate.getForObject(url, Policy[].class);
    }

    public Policy getPolicyById(String policyId) {
        Policy policy;
        String url = wiremockUrl1 + "/polizas/" + policyId;
        return policy = this.restTemplate.getForObject(url, Policy.class);
    }

    public String[] getPolicyConditions(String policyId) {
        String[] conditions;
        String url = wiremockUrl1 + "/polizas/" + policyId + "/condiciones";
        return conditions = this.restTemplate.getForObject(url, String[].class);
    }
}
