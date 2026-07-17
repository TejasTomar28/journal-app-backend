package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Autowired
    private AppCache appCache;

    @Value("${openai.api-key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String sendPrompt(String systemPrompt, String journalPrompt) {
        HttpHeaders headers = buildHeaders();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(buildRequestBody(systemPrompt, journalPrompt), headers);

        try {

            System.out.println("========== REQUEST BODY ==========");
            System.out.println(buildRequestBody(systemPrompt, journalPrompt));
            System.out.println("==================================");

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            getChatCompletionsUrl(),
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            System.out.println("HTTP STATUS = " + response.getStatusCode());

            System.out.println(response.getBody());

            return response.getBody();

        }
        catch (RestClientException exception) {

            System.out.println("========== OPENAI ERROR ==========");
            System.out.println(exception.getClass().getName());
            System.out.println(exception.getMessage());

            exception.printStackTrace();

            System.out.println("==================================");

            throw new OpenAIServiceException(
                    "OpenAI request failed",
                    exception
            );
        }
    }

    public String getConfiguredModel() {
        return getConfiguration(AppCache.Keys.OPENAI_MODEL);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private Map<String, Object> buildRequestBody(String systemPrompt, String journalPrompt) {
        return Map.of(
                "model", getConfiguredModel(),
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", journalPrompt)
                ),
                "temperature", getTemperature(),
                "max_tokens", getMaxTokens(),
                "response_format", Map.of("type", "json_object")
        );
    }

    private String getChatCompletionsUrl() {
        String baseUrl = getConfiguration(AppCache.Keys.OPENAI_BASE_URL);
        if (baseUrl.endsWith("/chat/completions")) {
            return baseUrl;
        }
        return baseUrl.endsWith("/") ? baseUrl + "chat/completions" : baseUrl + "/chat/completions";
    }

    private double getTemperature() {
        return parseDouble(AppCache.Keys.OPENAI_TEMPERATURE);
    }

    private int getMaxTokens() {
        return parseInteger(AppCache.Keys.OPENAI_MAX_TOKENS);
    }

    private double parseDouble(AppCache.Keys key) {
        try {
            return Double.parseDouble(getConfiguration(key));
        } catch (NumberFormatException exception) {
            throw new OpenAIServiceException("Invalid OpenAI configuration: " + key);
        }
    }

    private int parseInteger(AppCache.Keys key) {
        try {
            return Integer.parseInt(getConfiguration(key));
        } catch (NumberFormatException exception) {
            throw new OpenAIServiceException("Invalid OpenAI configuration: " + key);
        }
    }

    private String getConfiguration(AppCache.Keys key) {
        if (appCache.appCache == null) {
            throw new OpenAIServiceException("OpenAI configuration is not available");
        }

        String value = appCache.appCache.get(key.toString());
        if (value == null || value.trim().isEmpty()) {
            throw new OpenAIServiceException("Missing OpenAI configuration: " + key);
        }
        return value.trim();
    }
}
