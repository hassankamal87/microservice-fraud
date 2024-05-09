package org.example.fraud.repo;

import org.example.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudRepo extends JpaRepository<FraudCheckHistory, Integer> {
}
