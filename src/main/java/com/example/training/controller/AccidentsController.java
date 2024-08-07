package com.example.training.controller;

import com.example.training.model.dto.AccidentDto;
import com.example.training.service.AccidentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accidents")
public class AccidentsController {

    private final AccidentsService accidentsService;

    public AccidentsController(AccidentsService accidentsService) {
        this.accidentsService = accidentsService;
    }

    //http://localhost:8080/accidents/666/accidents
    @GetMapping("/{policyNumber}/accidents")
    public List<AccidentDto> getAccidentsByPolicy(@PathVariable String policyNumber) {
        return accidentsService.getAccidentsByPolicy(policyNumber);
    }

    // http://localhost:8080/accidents/666
    @GetMapping("/{accidentId}")
    public AccidentDto getAccidentById(@PathVariable String accidentId) {
        return accidentsService.getAccidentById(accidentId);
    }

}
