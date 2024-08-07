package com.example.training.model.dto;

import lombok.Data;

@Data
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
