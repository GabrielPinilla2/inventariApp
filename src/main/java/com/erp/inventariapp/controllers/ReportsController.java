package com.erp.inventariapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.erp.inventariapp.dto.SalesChartDTO;
import com.erp.inventariapp.services.SalesService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportsController {

    @Autowired
    private SalesService salesService;

    // Endpoint para gráfico mensual
    //@GetMapping("/monthly-sales")
    //public List<SalesChartDTO> getMonthlySales() {
    //    return salesService.getMonthlySales();
    //}
}
