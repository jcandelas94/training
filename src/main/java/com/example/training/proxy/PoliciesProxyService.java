package com.example.training.proxy;

import com.example.training.client.PoliciesClient;
import com.example.training.model.entity.Policy;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Service;

@Service
public class PoliciesProxyService {

    private final PoliciesClient policiesClient;

    public PoliciesProxyService() {
        this.policiesClient = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(PoliciesClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(PoliciesClient.class, "http://localhost:8081");
    }

    public Policy[] getPolicies(String dni) {
        return policiesClient.getPolicies(dni);
    }

    public Policy getPolicyById(String policyId) {
        return policiesClient.getPolicyById(policyId);
    }

    public String[] getPolicyConditions(String policyId) {
        return policiesClient.getPolicyConditions(policyId);
    }
}
