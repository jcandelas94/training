package com.example.training.service;

import com.example.training.mapper.AccidentMapper;
import com.example.training.model.dto.AccidentDto;
import com.example.training.model.entity.Accident;
import com.example.training.proxy.AccidentsProxyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccidentsService {

    private final AccidentsProxyService accidentsProxyService;

    public AccidentsService(AccidentsProxyService accidentsProxyService) {
        this.accidentsProxyService = accidentsProxyService;
    }

    public List<AccidentDto> getAccidentsByPolicy(String policyId) {
        Accident[] accidents = accidentsProxyService.getAccidentsByPolicy(policyId);
        List<Accident> accidentList = Arrays.asList(accidents);
        return AccidentMapper.INSTANCE.accidentsToAccidentDtos(accidentList);
    }

    public AccidentDto getAccidentById(String accidentId) {
        var accident = accidentsProxyService.getAccidentById(accidentId);
        return accident != null ? AccidentMapper.INSTANCE.accidentToAccidentDto(accident) : null;
    }

    public boolean isAccidentOwnedByPolicy(String accidentId, String policyId) {
        List<AccidentDto> accidents = getAccidentsByPolicy(policyId);
        return accidents.stream().anyMatch(accident -> accident.getAccidentId().equals(accidentId));
    }
}
