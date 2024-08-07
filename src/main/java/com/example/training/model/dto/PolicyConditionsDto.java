package com.example.training.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PolicyConditionsDto {
    private List<String> conditions;
}
