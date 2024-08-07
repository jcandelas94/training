package com.example.training.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class Accident {

    @Setter
    @Getter
    @JsonProperty("siniestroId")
    private String accidentId;

    @Setter
    @Getter
    @JsonProperty("estado")
    private String status;

    @Setter
    @Getter
    @JsonProperty("descripcion")
    private String description;
}
