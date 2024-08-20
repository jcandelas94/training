package com.example.training.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccidentDto {

    private String accidentId;
    private String description;
    private String state;

    public AccidentDto(String accidentId, String state) {
        this.accidentId = accidentId;
        this.state = state;
    }

    public AccidentDto(String accidentId, String description, String state) {
        this.accidentId = accidentId;
        this.description = description;
        this.state = state;
    }
}
