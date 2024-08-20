package com.example.training.service;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.entity.Accident;
import com.example.training.proxy.AccidentsProxyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class AccidentsService {

    private final AccidentsProxyService accidentsProxyService;

    public AccidentsService(AccidentsProxyService accidentsProxyService) {
        this.accidentsProxyService = accidentsProxyService;
    }

    public List<AccidentDto> getAccidentsByPolicy(String policyId) {
        var accidents = accidentsProxyService.getAccidentsByPolicy(policyId);
        return mapToAccidentDto(accidents);
    }

    public AccidentDto getAccidentById(String accidentId) {
        var accident = accidentsProxyService.getAccidentById(accidentId);
        return accident != null ? new AccidentDto(accident.getAccidentId(), accident.getDescription(), accident.getStatus()) : null;
    }

    private List<AccidentDto> mapToAccidentDto(Accident[] accidents) {
        return accidents != null ?
                List.of(accidents).stream()
                        .map(accident -> new AccidentDto(accident.getAccidentId(), accident.getDescription(), accident.getStatus()))
                        .collect(Collectors.toList()) :
                List.of();
    }

    public boolean isAccidentOwnedByPolicy(String accidentId, String policyId) {
        List<AccidentDto> accidents = getAccidentsByPolicy(policyId);
        return accidents.stream().anyMatch(accident -> accident.getAccidentId().equals(accidentId));
    }
}
