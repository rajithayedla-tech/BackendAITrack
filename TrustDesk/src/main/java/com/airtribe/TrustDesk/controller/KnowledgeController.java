package com.airtribe.TrustDesk.controller;

import com.airtribe.TrustDesk.dto.KnowledgeSearchResult;
import com.airtribe.TrustDesk.entity.KnowledgeDocument;
import com.airtribe.TrustDesk.service.KnowledgeRetrievalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeRetrievalService knowledgeRetrievalService;

    public KnowledgeController(
            KnowledgeRetrievalService knowledgeRetrievalService) {
        this.knowledgeRetrievalService = knowledgeRetrievalService;
    }

    @GetMapping
    public List<KnowledgeDocument> getAllDocuments() {
        return knowledgeRetrievalService.getAllDocuments();
    }


}