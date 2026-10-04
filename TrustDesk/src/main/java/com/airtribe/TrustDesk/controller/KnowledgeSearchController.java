package com.airtribe.TrustDesk.controller;

import com.airtribe.TrustDesk.dto.KnowledgeSearchResult;
import com.airtribe.TrustDesk.service.KnowledgeRetrievalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/knowledge")
public class KnowledgeSearchController {

    private final KnowledgeRetrievalService
            knowledgeRetrievalService;

    public KnowledgeSearchController(
            KnowledgeRetrievalService knowledgeRetrievalService) {

        this.knowledgeRetrievalService =
                knowledgeRetrievalService;
    }

    @GetMapping("/search")
    public List<KnowledgeSearchResult> search(
            @RequestParam String query) {

        return knowledgeRetrievalService.search(query);
    }
}