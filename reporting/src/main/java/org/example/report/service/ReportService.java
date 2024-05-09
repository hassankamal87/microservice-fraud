package org.example.report.service;

import org.example.report.model.Report;
import org.example.report.model.ReportRequest;
import org.example.report.repo.ReportRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record ReportService(ReportRepo reportRepo, RestTemplate restTemplate) {

    public void createReport(ReportRequest reportRequest) {
        Report report = Report.builder()
                .description(reportRequest.description())
                .build();
        reportRepo.save(report);
    }
}
