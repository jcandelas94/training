package com.example.training.client;

import com.example.training.model.entity.Policy;
import feign.Param;
import feign.RequestLine;

public interface PoliciesClient {

    @RequestLine("GET /polizas?dni={dni}")
    Policy[] getPolicies(@Param("dni") String dni);

    @RequestLine("GET /polizas/{policyId}")
    Policy getPolicyById(@Param("policyId") String policyId);

    @RequestLine("GET /polizas/{policyId}/condiciones")
    String[] getPolicyConditions(@Param("policyId") String policyId);

}
