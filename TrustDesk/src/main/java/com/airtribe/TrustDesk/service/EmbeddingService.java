package com.airtribe.TrustDesk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final RestClient restClient;
    private final String model;

    public EmbeddingService(
            @Value("${trustdesk.ollama.url}") String ollamaUrl,
            @Value("${trustdesk.ollama.model}") String model) {

        this.restClient = RestClient.builder()
                .baseUrl(ollamaUrl)
                .build();

        this.model = model;
    }

    public List<Double> generateEmbedding(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "Text cannot be empty when generating embedding"
            );
        }

        Map<String, Object> request = Map.of(
                "model", model,
                "input", text
        );

        Map<?, ?> response = restClient.post()
                .uri("/api/embed")
                .body(request)
                .retrieve()
                .body(Map.class);

        if (response == null) {
            throw new IllegalStateException(
                    "Ollama returned an empty response"
            );
        }

        Object embeddingsObject = response.get("embeddings");

        if (!(embeddingsObject instanceof List<?> embeddings)
                || embeddings.isEmpty()) {

            throw new IllegalStateException(
                    "Ollama returned no embeddings"
            );
        }

        Object firstEmbedding = embeddings.get(0);

        if (!(firstEmbedding instanceof List<?> values)) {
            throw new IllegalStateException(
                    "Invalid embedding response from Ollama"
            );
        }

        return values.stream()
                .map(value -> ((Number) value).doubleValue())
                .toList();
    }
}