package org.example.fraud.service;

import lombok.AllArgsConstructor;
import org.example.fraud.model.FraudCheckHistory;
import org.example.fraud.repo.FraudRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudService {
    private final FraudRepo fraudRepo;
    public boolean isFraudulentCustomer(int customerId){
        fraudRepo.save(FraudCheckHistory.builder()
                .isFraudster(false)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
