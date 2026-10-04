package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.dto.KnowledgeSearchResult;
import com.airtribe.TrustDesk.entity.KnowledgeDocument;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.repository.KnowledgeDocumentRepository;
import com.airtribe.TrustDesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeRetrievalService {

    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final TicketRepository ticketRepository;
    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public KnowledgeRetrievalService(
            KnowledgeDocumentRepository knowledgeDocumentRepository,
            TicketRepository ticketRepository,
            EmbeddingService embeddingService,
            QdrantService qdrantService) {

        this.knowledgeDocumentRepository =
                knowledgeDocumentRepository;

        this.ticketRepository =
                ticketRepository;

        this.embeddingService =
                embeddingService;

        this.qdrantService =
                qdrantService;
    }

    public List<KnowledgeDocument> getAllDocuments() {
        return knowledgeDocumentRepository.findAll();
    }

    public List<KnowledgeSearchResult> search(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }

        List<Double> embedding =
                embeddingService.generateEmbedding(query);

        List<QdrantService.QdrantSearchResult> results =
                qdrantService.search(
                        embedding,
                        5
                );

        return results.stream()
                .map(result -> {

                    Map<String, Object> payload =
                            result.payload();

                    String documentId =
                            String.valueOf(
                                    payload.get("document_id")
                            );

                    String title =
                            String.valueOf(
                                    payload.get("title")
                            );

                    String content =
                            String.valueOf(
                                    payload.get("content")
                            );

                    return new KnowledgeSearchResult(
                            documentId,
                            title,
                            createSnippet(content),
                            result.score()
                    );
                })
                .toList();
    }

    private String createSnippet(String content) {

        if (content == null) {
            return "";
        }

        if (content.length() <= 500) {
            return content;
        }

        return content.substring(0, 500) + "...";
    }

    /**
     * Retrieves knowledge relevant to a specific ticket.
     *
     * The ticket's subject and body are used as the retrieval query.
     * Expected category/priority are intentionally not used because
     * those fields represent evaluation ground truth, not runtime
     * classification results.
     */
    public List<KnowledgeSearchResult> getKnowledgeForTicket(
            String ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found: " + ticketId
                        )
                );

        String query = normalize(
                ticket.getSubject() + " " + ticket.getBody()
        );

        return search(query)
                .stream()
                .limit(5)
                .toList();
    }

    private String normalize(String text) {

        if (text == null) {
            return "";
        }

        return text
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", " ")
                .trim();
    }

    private String createSnippet(
            String content,
            List<String> queryWords) {

        String[] paragraphs = content.split("\\n\\s*\\n");

        for (String paragraph : paragraphs) {

            String lowerParagraph =
                    paragraph.toLowerCase(Locale.ROOT);

            for (String word : queryWords) {

                if (lowerParagraph.contains(word)) {
                    return paragraph.trim();
                }
            }
        }

        return content.length() > 300
                ? content.substring(0, 300) + "..."
                : content;
    }
}