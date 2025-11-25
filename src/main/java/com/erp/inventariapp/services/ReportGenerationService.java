package com.erp.inventariapp.services;

import com.erp.inventariapp.entities.SalesReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportGenerationService {

    private final SalesReportService salesReportService;
    private final AIPredictionService aiService;

    public SalesReport generate(LocalDate start, LocalDate end) {

        SalesReport report = salesReportService.generateStatistics(start, end);

        String aiPrediction = aiService.predict(report.getMostSoldProducts());

        report.setAiPrediction(aiPrediction);

        return report;
    }
}

