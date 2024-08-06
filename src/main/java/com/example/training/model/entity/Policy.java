package com.example.training.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Policy {

    @Setter
    @Getter
    @JsonProperty("polizaId")
    private String policyId;

    @Setter
    @Getter
    @JsonProperty("descripcion")
    private String description;

    @Setter
    @Getter
    @JsonProperty("coberturas")
    private List<String> coverages;

    @Setter
    @Getter
    @JsonProperty("condiciones")
    private List<String> conditions;

}
