package com.erp.inventariapp.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    
    @GetMapping("/")
    public String hello() {
        return "*** Jayson Backend is running ***";
    }
}