package com.example.training.proxy;

import com.example.training.model.entity.Accident;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccidentsProxyService {

    private final RestTemplate restTemplate;
    private final String wiremockUrl1 = "http://localhost:8081";

    public AccidentsProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Accident[] getAccidentsByPolicy(String policyId) {
        Accident[] accidents;
        String url = wiremockUrl1 + "/polizas/" + policyId + "/siniestros";
        return accidents = this.restTemplate.getForObject(url, Accident[].class);
    }

    public Accident getAccidentById(String accidentId) {
        Accident accident;
        String url = wiremockUrl1 + "/siniestros/" + accidentId;
        return accident = this.restTemplate.getForObject(url, Accident.class);
    }

}
