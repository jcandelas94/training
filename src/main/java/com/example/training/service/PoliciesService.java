package com.example.training.service;

import com.example.training.mapper.PolicyMapper;
import com.example.training.model.dto.PolicyDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.model.entity.Policy;
import com.example.training.proxy.PoliciesProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoliciesService {

    private final PoliciesProxyService policiesProxyService;
    private static final Logger log = LoggerFactory.getLogger(PoliciesService.class);

    public PoliciesService(PoliciesProxyService policiesProxyService) {
        this.policiesProxyService = policiesProxyService;
    }

    @PreAuthorize("#userId == authentication.name")
    @Cacheable(value = "policiesCache")
    public PolicyWrapperDto getPolicies(String userId) {

        log.info("Executing getPolicies for user: {}", userId);

        Policy[] policies = policiesProxyService.getPolicies(userId);
        List<Policy> policyList = Arrays.asList(policies);
        List<PolicyDto> policyDtos = PolicyMapper.INSTANCE.policiesToPolicyDtos(policyList);

        return PolicyWrapperDto.builder()
                .policies(policyDtos)
                .build();
    }

    public PolicyWrapperDto getPolicyById(String policyId) {

        var policy = policiesProxyService.getPolicyById(policyId);
        if (policy != null) {
            PolicyDto policyDto = PolicyMapper.INSTANCE.policyToPolicyDto(policy);
            return PolicyWrapperDto.builder()
                    .policies(List.of(policyDto))
                    .build();
        }
        return PolicyWrapperDto.builder()
                .policies(List.of())
                .build();
    }

    @Cacheable(value = "policiesOwnershipCache")
    public boolean isPolicyOwnedByUser(String policyId, String userId) {
        List<PolicyDto> userPolicies = getPolicies(userId).getPolicies();
        log.info("Executing isPolicyOwnedByUser for user: {}", userId);
        return userPolicies.stream().anyMatch(policy -> policy.getPolicyId().equals(policyId));
    }
}