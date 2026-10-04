package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.entity.KnowledgeDocument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeIngestionService {

    private final KnowledgeRetrievalService knowledgeRetrievalService;
    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public KnowledgeIngestionService(
            KnowledgeRetrievalService knowledgeRetrievalService,
            EmbeddingService embeddingService,
            QdrantService qdrantService) {

        this.knowledgeRetrievalService =
                knowledgeRetrievalService;

        this.embeddingService =
                embeddingService;

        this.qdrantService =
                qdrantService;
    }

    public int ingestAllDocuments() {

        qdrantService.createCollectionIfNotExists();

        List<KnowledgeDocument> documents =
                knowledgeRetrievalService.getAllDocuments();

        int count = 0;

        for (KnowledgeDocument document : documents) {

            String textToEmbed = buildEmbeddingText(document);

            List<Double> embedding =
                    embeddingService.generateEmbedding(textToEmbed);

            qdrantService.upsert(
                    document,
                    embedding
            );

            count++;
        }

        return count;
    }

    private String buildEmbeddingText(
            KnowledgeDocument document) {

        return """
                Title:
                %s

                Audience:
                %s

                Content:
                %s
                """.formatted(
                safe(document.getTitle()),
                safe(document.getAudience()),
                safe(document.getContent())
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}