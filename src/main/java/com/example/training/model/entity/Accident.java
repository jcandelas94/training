package com.example.training.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class Accident {

    @Setter
    @Getter
    @JsonProperty("siniestroId")
    private String claimId;

    @Setter
    @Getter
    @JsonProperty("descripcion")
    private String description;
}
