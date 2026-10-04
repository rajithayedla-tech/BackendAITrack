package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.entity.KnowledgeDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QdrantService {

    private final RestClient restClient;
    private final String collection;
    private final int dimension;

    public QdrantService(
            @Value("${trustdesk.qdrant.url}") String qdrantUrl,
            @Value("${trustdesk.qdrant.collection}") String collection,
            @Value("${trustdesk.qdrant.dimension}") int dimension) {

        this.restClient = RestClient.builder()
                .baseUrl(qdrantUrl)
                .build();

        this.collection = collection;
        this.dimension = dimension;
    }

    public void createCollectionIfNotExists() {

        try {

            restClient.get()
                    .uri("/collections/{collection}", collection)
                    .retrieve()
                    .toBodilessEntity();

            // Collection already exists.

        } catch (Exception e) {

            Map<String, Object> vectorConfig = Map.of(
                    "size", dimension,
                    "distance", "Cosine"
            );

            Map<String, Object> body = Map.of(
                    "vectors", vectorConfig
            );

            restClient.put()
                    .uri("/collections/{collection}", collection)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    public void upsert(
            KnowledgeDocument document,
            List<Double> embedding) {

        String pointId = UUID.nameUUIDFromBytes(
                document.getDocumentId()
                        .getBytes(StandardCharsets.UTF_8)
        ).toString();

        Map<String, Object> payload = Map.of(
                "document_id", document.getDocumentId(),
                "title", safe(document.getTitle()),
                "content", safe(document.getContent()),
                "audience", safe(document.getAudience())
        );

        Map<String, Object> point = Map.of(
                "id", pointId,
                "vector", embedding,
                "payload", payload
        );

        Map<String, Object> body = Map.of(
                "points", List.of(point)
        );

        restClient.put()
                .uri("/collections/{collection}/points", collection)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

    public List<QdrantSearchResult> search(
            List<Double> embedding,
            int limit) {

        Map<String, Object> body = Map.of(
                "vector", embedding,
                "limit", limit,
                "with_payload", true
        );

        QdrantSearchResponse response =
                restClient.post()
                        .uri("/collections/{collection}/points/search",
                                collection)
                        .body(body)
                        .retrieve()
                        .body(QdrantSearchResponse.class);

        if (response == null || response.result() == null) {
            return List.of();
        }

        return response.result();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public record QdrantSearchResponse(
            List<QdrantSearchResult> result
    ) {
    }

    public record QdrantSearchResult(
            String id,
            double score,
            Map<String, Object> payload
    ) {
    }
}