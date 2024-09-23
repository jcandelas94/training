package com.example.training.client;

import com.example.training.model.entity.Accident;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "accidentsClient", url = "${accidents.service.url}")
public interface AccidentsClient {

    @CircuitBreaker(name = "accidents")
    @GetMapping("/polizas/{policyId}/siniestros")
    List<Accident> getAccidentsByPolicy(@PathVariable("policyId") String policyId);

    @GetMapping("/siniestros/{siniestroId}")
    Accident getAccidentById(@PathVariable("siniestroId") String policyId);
}
