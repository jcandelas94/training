package com.example.training.model.dto;

import lombok.Data;

@Data
public class PolicyDto {

    private String polizaId;
    private String descripcion;

    public PolicyDto(String polizaId, String descripcion) {
        this.polizaId = polizaId;
        this.descripcion = descripcion;
    }
}
