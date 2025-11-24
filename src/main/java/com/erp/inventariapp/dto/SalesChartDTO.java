package com.erp.inventariapp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SalesChartDTO {

    private String month;
    private Double total;

    public String getMonth() {
        return month;
    }

    public Double getTotal() {
        return total;
    }
}
