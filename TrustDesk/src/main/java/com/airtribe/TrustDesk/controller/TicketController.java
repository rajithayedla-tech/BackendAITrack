package com.airtribe.TrustDesk.controller;

import com.airtribe.TrustDesk.dto.DraftResponse;
import com.airtribe.TrustDesk.dto.KnowledgeSearchResult;
import com.airtribe.TrustDesk.dto.TriageResult;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.service.DraftGenerationService;
import com.airtribe.TrustDesk.service.KnowledgeRetrievalService;
import com.airtribe.TrustDesk.service.TicketService;
import com.airtribe.TrustDesk.service.TriageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final KnowledgeRetrievalService knowledgeRetrievalService;
    private final TriageService triageService;
    private final DraftGenerationService draftGenerationService;

    public TicketController(
            TicketService ticketService,
            KnowledgeRetrievalService knowledgeRetrievalService,
            TriageService triageService,
            DraftGenerationService draftGenerationService) {

        this.ticketService = ticketService;
        this.knowledgeRetrievalService = knowledgeRetrievalService;
        this.triageService = triageService;
        this.draftGenerationService = draftGenerationService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicket(
            @PathVariable String ticketId) {

        return ResponseEntity.ok(
                ticketService.getTicket(ticketId)
        );
    }

    @GetMapping("/{ticketId}/knowledge")
    public ResponseEntity<List<KnowledgeSearchResult>> getKnowledgeForTicket(
            @PathVariable String ticketId) {

        return ResponseEntity.ok(
                knowledgeRetrievalService
                        .getKnowledgeForTicket(ticketId)
        );
    }

    @GetMapping("/{ticketId}/triage")
    public ResponseEntity<TriageResult> triage(
            @PathVariable String ticketId) {

        return ResponseEntity.ok(
                triageService.triage(ticketId)
        );
    }

    @GetMapping("/{ticketId}/draft")
    public ResponseEntity<DraftResponse> generateDraft(
            @PathVariable String ticketId) {

        return ResponseEntity.ok(
                draftGenerationService.generateDraft(ticketId)
        );
    }
}