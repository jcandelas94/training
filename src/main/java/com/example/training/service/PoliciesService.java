package com.example.training.service;

import com.example.training.client.PoliciesClient;
import com.example.training.mapper.PolicyMapper;
import com.example.training.model.dto.PolicyDto;
import com.example.training.model.entity.Policy;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PoliciesService {

    private static final Logger log = LoggerFactory.getLogger(PoliciesService.class);
    private final PoliciesClient policiesClient;

    public PoliciesService() {
        this.policiesClient = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(PoliciesClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(PoliciesClient.class, "http://localhost:8081");
    }

    @PreAuthorize("#userId == authentication.name")
    @Cacheable(value = "policiesCache")
    public List<PolicyDto> getPolicies(String userId) {

        log.info("Executing getPolicies for user: {}", userId);

        Policy[] policies = policiesClient.getPolicies(userId);
        List<Policy> policyList = Arrays.asList(policies);
        List<PolicyDto> policyDtos = PolicyMapper.INSTANCE.policiesToPolicyDtos(policyList);
        return policyDtos;
    }

    public PolicyDto getPolicyById(String policyId) {

        var policy = policiesClient.getPolicyById(policyId);
        if (policy != null) {
            PolicyDto policyDto = PolicyMapper.INSTANCE.policyToPolicyDto(policy);
            return policyDto;
        }
        return new PolicyDto();
    }

    @Cacheable(value = "policiesOwnershipCache")
    public boolean isPolicyOwnedByUser(String policyId, String userId) {
        List<PolicyDto> userPolicies = getPolicies(userId);
        log.info("Executing isPolicyOwnedByUser for user: {}", userId);
        return userPolicies.stream().anyMatch(policy -> policy.getPolicyId().equals(policyId));
    }
}