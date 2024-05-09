package org.example.customer.service;

import org.example.customer.model.Customer;
import org.example.customer.model.CustomerRequest;
import org.example.customer.model.FraudCheckResponse;
import org.example.customer.repo.CustomerRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepo customerRepo, RestTemplate restTemplate) {
    public void registerCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email()).build();

        // todo: check if email valid
        // todo: check if email not token

        //todo: save customer to db
        customerRepo.saveAndFlush(customer);

        // todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        //todo: send notification

    }
}
