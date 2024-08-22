package com.example.training.service;

import com.example.training.client.AccidentsClient;
import com.example.training.mapper.AccidentMapper;
import com.example.training.model.dto.AccidentDto;
import com.example.training.model.entity.Accident;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccidentsService {

    private final AccidentsClient accidentsClient;

    public AccidentsService() {
        this.accidentsClient = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(AccidentsClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(AccidentsClient.class, "http://localhost:8081");
    }

    public List<AccidentDto> getAccidentsByPolicy(String policyId) {
        Accident[] accidents = accidentsClient.getAccidentsByPolicy(policyId);
        List<Accident> accidentList = Arrays.asList(accidents);
        return AccidentMapper.INSTANCE.accidentsToAccidentDtos(accidentList);
    }

    public AccidentDto getAccidentById(String accidentId) {
        var accident = accidentsClient.getAccidentById(accidentId);
        return accident != null ? AccidentMapper.INSTANCE.accidentToAccidentDto(accident) : null;
    }

    public boolean isAccidentOwnedByPolicy(String accidentId, String policyId) {
        List<AccidentDto> accidents = getAccidentsByPolicy(policyId);
        return accidents.stream().anyMatch(accident -> accident.getAccidentId().equals(accidentId));
    }
}
