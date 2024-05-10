package org.example.customer.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.example.customer.model.Customer;
import org.example.customer.model.CustomerRequest;
import org.example.customer.model.FraudCheckResponse;
import org.example.customer.model.ReportRequest;
import org.example.customer.repo.CustomerRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepo customerRepo, RestTemplate restTemplate, EurekaClient eurekaClient) {


    public void registerCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email()).build();


        customerRepo.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject( createBaseUrlForService("fraud")+ "/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        ReportRequest reportRequest = new ReportRequest("A Fraud with id: " + customer.getId() + " tries to log in the system");

        restTemplate.postForEntity(createBaseUrlForService("reports") + "/api/v1/reports/",
                reportRequest,
                Void.class
        );

    }
    public String createBaseUrlForService(String serviceName) {
        InstanceInfo serviceInstance = eurekaClient
                .getApplication(serviceName)
                .getInstances()
                .get(0);

        String hostName = serviceInstance.getHostName();
        int port = serviceInstance.getPort();

        return "http://" + hostName + ":" + port;
    }
}
