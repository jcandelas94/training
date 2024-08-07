package com.example.training.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PolicyDto {

    private String policyId;
    private String description;
    private List<String> coverages;
    private List<String> conditions;
    public PolicyDto(String policyId, String description) {
        this.policyId = policyId;
        this.description = description;
    }

    public PolicyDto(String policyId, String description, List<String> coverages) {
        this.policyId = policyId;
        this.description = description;
        this.coverages = coverages;
    }
}
