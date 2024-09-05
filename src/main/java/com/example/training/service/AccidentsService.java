package com.example.training.service;

import com.example.training.client.AccidentsClient;
import com.example.training.mapper.AccidentMapper;
import com.example.training.model.dto.AccidentDto;
import com.example.training.model.entity.Accident;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccidentsService {

    private final AccidentsClient accidentsClient;

    @Autowired
    public AccidentsService(AccidentsClient accidentsClient) {
        this.accidentsClient = accidentsClient;
    }

    @CircuitBreaker(name = "accidents", fallbackMethod = "fallbackMethod")
    public List<AccidentDto> getAccidentsByPolicy(String policyId) {
        List<Accident> accidents = accidentsClient.getAccidentsByPolicy(policyId);
        return AccidentMapper.INSTANCE.accidentsToAccidentDtos(accidents);
    }

    public List<Accident> fallbackMethod(String policyId, Throwable t) {
        log.error("Circuit Breaker Fallback triggered. Exception: {}", t.getMessage());
        return Collections.emptyList();
    }

    public AccidentDto getAccidentById(String accidentId) {
        var accident = accidentsClient.getAccidentById(accidentId);
        if (accident == null) {
            throw new NoSuchElementException("Accident wasn't found with id: " + accidentId);
        }
        return AccidentMapper.INSTANCE.accidentToAccidentDto(accident);
    }

    public boolean isAccidentOwnedByPolicy(String accidentId, String policyId) {
        List<AccidentDto> accidents = getAccidentsByPolicy(policyId);
        return accidents.stream().anyMatch(accident -> accident.getAccidentId().equals(accidentId));
    }
}
