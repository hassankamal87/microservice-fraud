package org.example.fraud.controller;

import lombok.AllArgsConstructor;
import org.example.fraud.model.FraudCheckResponse;
import org.example.fraud.service.FraudService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
public class FraudController {

    private final FraudService fraudService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") int customerID){

        boolean isFraudulentCustomer = fraudService.isFraudulentCustomer(customerID);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
