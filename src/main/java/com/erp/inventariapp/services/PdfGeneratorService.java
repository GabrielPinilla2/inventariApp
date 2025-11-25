package com.erp.inventariapp.services;

import com.erp.inventariapp.entities.SalesReport;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generatePdf(SalesReport report) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Sales Report"));
        document.add(new Paragraph(report.getPeriodDescription()));
        document.add(new Paragraph("\nMost Sold Products:"));

        report.getMostSoldProducts().forEach((k, v) -> {
            try { document.add(new Paragraph(k + ": " + v)); }
            catch (Exception ignored) {}
        });

        document.add(new Paragraph("\nAI Prediction:"));
        document.add(new Paragraph(report.getAiPrediction()));

        document.close();
        return out.toByteArray();
    }
}

