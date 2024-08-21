package com.example.training.proxy;

import com.example.training.client.AccidentsClient;
import com.example.training.model.entity.Accident;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Service;

@Service
public class AccidentsProxyService {

    private final AccidentsClient accidentsClient;

    public AccidentsProxyService() {
        this.accidentsClient = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(AccidentsClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(AccidentsClient.class, "http://localhost:8081");
    }

    public Accident[] getAccidentsByPolicy(String policyId) {
        return accidentsClient.getAccidentsByPolicy(policyId);
    }

    public Accident getAccidentById(String accidentId) {
        return accidentsClient.getAccidentById(accidentId);
    }

}
