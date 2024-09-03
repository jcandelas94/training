package com.example.training.client;

import com.example.training.model.entity.Policy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "policiesClient", url = "${policies.service.url}")
public interface PoliciesClient {

    @GetMapping("/polizas?dni={dni}")
    Policy[] getPolicies(@RequestParam("dni") String dni);

    @GetMapping("/polizas/{policyId}")
    Policy getPolicyById(@PathVariable("policyId") String policyId);

    @GetMapping("/polizas/{policyId}/condiciones")
    String[] getPolicyConditions(@PathVariable("policyId") String policyId);
}
