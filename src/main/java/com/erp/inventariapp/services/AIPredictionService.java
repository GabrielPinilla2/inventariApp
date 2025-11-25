package com.erp.inventariapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIPredictionService {

    @Value("${ai.remote.url}")
    private String apiUrl;

    @Value("${ai.remote.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String predict(Map<String, Long> history) {

        String input =
                "Historical sales: " + history +
                        "\nPredict the best-selling product next month and explain why.";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> body = Map.of("inputs", input);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        List<Map<String, Object>> response =
                restTemplate.postForObject(apiUrl, request, List.class);

        return response.get(0).get("generated_text").toString();
    }
}

