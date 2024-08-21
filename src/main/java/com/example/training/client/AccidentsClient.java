package com.example.training.client;

import com.example.training.model.entity.Accident;
import feign.Param;
import feign.RequestLine;

public interface AccidentsClient {

    @RequestLine("GET /polizas/{policyId}/siniestros")
    Accident[] getAccidentsByPolicy(@Param("policyId") String policyId);

    @RequestLine("GET /siniestros/{siniestroId}")
    Accident getAccidentById(@Param("siniestroId") String policyId);
}
