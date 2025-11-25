package com.erp.inventariapp.entities;

import lombok.Data;

import java.util.Map;

@Data
public class SalesReport {
    private String periodDescription;
    private Map<String, Long> mostSoldProducts;
    private Map<String, Double> revenueByProduct;
    private String aiPrediction;
}
