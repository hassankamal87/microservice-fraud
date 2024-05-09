package org.example.report.repo;

import org.example.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report,Integer> {
}
