package org.example.customer.service;

import org.example.customer.model.Customer;
import org.example.customer.model.CustomerRequest;
import org.example.customer.model.FraudCheckResponse;
import org.example.customer.model.ReportRequest;
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


        customerRepo.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        ReportRequest reportRequest = new ReportRequest("A Fraud with id: " + customer.getId() + " tries to log in the system");

        restTemplate.postForEntity("http://localhost:8081/api/v1/reports/",
                reportRequest,
                Void.class
        );
    }
}
