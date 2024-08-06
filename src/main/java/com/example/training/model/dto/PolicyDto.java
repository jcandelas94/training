package com.example.training.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PolicyDto {

    private String policyId;
    private String description;
    private List<String> conditions;


    public PolicyDto(String polizaId, String descripcion) {
        this.policyId = polizaId;
        this.description = descripcion;
    }

    public PolicyDto(String polizaId, String descripcion, List<String> condiciones) {
        this.policyId = polizaId;
        this.description = descripcion;
        this.conditions = condiciones;
    }
}
