package com.erp.inventariapp.controllers;

import com.erp.inventariapp.entities.SalesReport;
import com.erp.inventariapp.services.PdfGeneratorService;
import com.erp.inventariapp.services.ReportGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportsController {

    private final ReportGenerationService reportService;
    private final PdfGeneratorService pdfService;

    public ReportsController(
            ReportGenerationService reportService,
            PdfGeneratorService pdfService) {
        this.reportService = reportService;
        this.pdfService = pdfService;
    }

    @GetMapping("/sales")
    public SalesReport getReport(
            @RequestParam String start,
            @RequestParam String end) {

        return reportService.generate(
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }

    @GetMapping(value="/sales/pdf", produces="application/pdf")
    public ResponseEntity<byte[]> getPdf(
            @RequestParam String start,
            @RequestParam String end) throws Exception {

        SalesReport report = reportService.generate(
                LocalDate.parse(start),
                LocalDate.parse(end)
        );

        byte[] pdf = pdfService.generatePdf(report);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=sales-report.pdf")
                .body(pdf);
    }
}
