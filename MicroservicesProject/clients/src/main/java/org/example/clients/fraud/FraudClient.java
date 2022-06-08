package org.example.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        "fraud"
)
public interface FraudClient {
    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    // this is an interface which targets the fraud controller inside fraud ms
     FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerID);
}
