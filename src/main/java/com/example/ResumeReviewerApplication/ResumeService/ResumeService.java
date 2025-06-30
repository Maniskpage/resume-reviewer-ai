package com.example.ResumeReviewerApplication.ResumeService;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ResumeService {
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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"; // Your OpenRouter key
    private static final String DEEPSEEK_MODEL = "openai/gpt-4.1";

    public String reviewWithAI(String resumeText) {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("HTTP-Referer", "http://localhost:8080/review"); // Required
            headers.set("X-Title", "ResumeReviewer"); // Optional

            String prompt = "Analyze this resume and provide structured improvement suggestions:\n\n" + resumeText;

            Map<String, Object> requestBody = Map.of(
                    "model", DEEPSEEK_MODEL,
                    "messages", List.of(Map.of("role", "user", "content", prompt)),
                    "temperature", 0.3, // Less randomness, more factual
                    "max_tokens", 1000  // Allows detailed responses
            );

            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(
                        OPENROUTER_API_URL,
                        new HttpEntity<>(requestBody, headers),
                        Map.class
                );

                if (response.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> responseBody = response.getBody();
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                } else {
                    return "API Error: " + response.getStatusCode();
                }
            } catch (Exception e) {
                return "Request Failed: " + e.getMessage();
            }
        }
    }