package com.erp.inventariapp.services;

import com.erp.inventariapp.entities.SaleProduct;
import com.erp.inventariapp.entities.SalesReport;
import com.erp.inventariapp.repositories.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReportService {

    private final SalesRepository saleRepository;

    public SalesReport generateStatistics(LocalDate start, LocalDate end) {
        var sales = saleRepository.findByDateBetween(start, end);

        var mostSold = sales.stream()
                .flatMap(s -> s.getSaleProducts().stream())
                .collect(Collectors.groupingBy(
                        sp -> sp.getProduct().getName(),
                        Collectors.summingLong(SaleProduct::getQuantity)
                ));

        var revenue = sales.stream()
                .flatMap(s -> s.getSaleProducts().stream())
                .collect(Collectors.groupingBy(
                        sp -> sp.getProduct().getName(),
                        Collectors.summingDouble(sp -> sp.getQuantity() * sp.getProduct().getPrice())
                ));

        SalesReport report = new SalesReport();
        report.setPeriodDescription(start + " -> " + end);
        report.setMostSoldProducts(mostSold);
        report.setRevenueByProduct(revenue);

        return report;
    }
}

