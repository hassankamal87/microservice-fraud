package org.example.report.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.report.model.ReportRequest;
import org.example.report.service.ReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/reports")
public record ReportController(ReportService reportService) {
    @PostMapping
    public void registerCustomer(@RequestBody ReportRequest reportRequest){
        log.info("new report generated {}", reportRequest);
        reportService.createReport(reportRequest);
    }
}