package com.airtribe.TrustDesk.config;

import com.airtribe.TrustDesk.service.KnowledgeIngestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VectorDatabaseInitializer
        implements CommandLineRunner {

    private final KnowledgeIngestionService
            knowledgeIngestionService;

    public VectorDatabaseInitializer(
            KnowledgeIngestionService knowledgeIngestionService) {

        this.knowledgeIngestionService =
                knowledgeIngestionService;
    }

    @Override
    public void run(String... args) {

        System.out.println(
                "TrustDesk vector database ingestion started."
        );

        try {

            int count =
                    knowledgeIngestionService
                            .ingestAllDocuments();

            System.out.println(
                    "Knowledge documents embedded: "
                            + count
            );

            System.out.println(
                    "TrustDesk vector database ingestion completed."
            );

        } catch (Exception e) {

            System.err.println(
                    "Vector database ingestion failed: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}