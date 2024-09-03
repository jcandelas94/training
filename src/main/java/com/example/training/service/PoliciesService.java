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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PoliciesService {

    private static final Logger log = LoggerFactory.getLogger(PoliciesService.class);
    private final PoliciesClient policiesClient;

    @Autowired
    public PoliciesService(PoliciesClient policiesClient) {
        this.policiesClient = policiesClient;
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
        if (policy == null) {
            throw new NoSuchElementException("Policy wasn't found with id: " + policyId);
        }
        return PolicyMapper.INSTANCE.policyToPolicyDto(policy);
    }

    @Cacheable(value = "policiesOwnershipCache")
    public boolean isPolicyOwnedByUser(String policyId, String userId) {
        List<PolicyDto> userPolicies = getPolicies(userId);
        log.info("Executing isPolicyOwnedByUser for user: {}", userId);
        return userPolicies.stream().anyMatch(policy -> policy.getPolicyId().equals(policyId));
    }
}