package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.dto.TriageResult;
import com.airtribe.TrustDesk.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TriageService {

    private final TicketService ticketService;

    public TriageService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public TriageResult triage(String ticketId) {

        Ticket ticket = ticketService.getTicket(ticketId);

        String text = (
                safe(ticket.getSubject()) + " " +
                        safe(ticket.getBody())
        ).toLowerCase(Locale.ROOT);

        String category = classifyCategory(text);
        String priority = classifyPriority(text);
        boolean escalation = shouldEscalate(text, category);

        String reason = buildReason(
                text,
                category,
                priority,
                escalation
        );

        return new TriageResult(
                category,
                priority,
                escalation,
                reason
        );
    }

    private String classifyCategory(String text) {

        if (containsAny(
                text,
                "password",
                "account",
                "email address",
                "identity",
                "verification",
                "api key",
                "system prompt",
                "internal notes"
        )) {
            return "account_security";
        }

        if (containsAny(
                text,
                "charged twice",
                "double charge",
                "duplicate charge",
                "billing",
                "payment"
        )) {
            return "billing";
        }

        if (containsAny(
                text,
                "battery",
                "warranty",
                "overheating",
                "swollen",
                "swelling",
                "burning smell"
        )) {
            return "warranty";
        }

        if (containsAny(
                text,
                "tracking",
                "delivery",
                "delayed",
                "shipment",
                "carrier",
                "package"
        )) {
            return "shipping";
        }

        if (containsAny(
                text,
                "refund",
                "return",
                "damaged",
                "defective",
                "replacement",
                "final sale"
        )) {
            return "refund";
        }

        return "general";
    }

    private String classifyPriority(String text) {

        if (containsAny(
                text,
                "battery swelling",
                "swollen battery",
                "overheating",
                "burning smell",
                "electric shock",
                "exposed wires",
                "security breach",
                "account takeover"
        )) {
            return "urgent";
        }

        if (containsAny(
                text,
                "double charge",
                "duplicate charge",
                "no tracking movement",
                "not moving",
                "urgent",
                "travel tomorrow",
                "security"
        )) {
            return "high";
        }

        if (containsAny(
                text,
                "damaged",
                "defective",
                "refund",
                "replacement"
        )) {
            return "medium";
        }

        return "low";
    }

    private boolean shouldEscalate(
            String text,
            String category) {

        if ("account_security".equals(category)) {
            return true;
        }

        if ("warranty".equals(category)
                && containsAny(
                text,
                "battery swelling",
                "swollen",
                "overheating",
                "burning smell",
                "electric shock",
                "exposed wires"
        )) {
            return true;
        }

        if (containsAny(
                text,
                "system prompt",
                "api key",
                "internal notes",
                "reveal secrets",
                "ignore previous instructions",
                "ignore policies",
                "hide from human"
        )) {
            return true;
        }

        return false;
    }

    private String buildReason(
            String text,
            String category,
            String priority,
            boolean escalation) {

        return "Classified as "
                + category
                + " with "
                + priority
                + " priority."
                + (escalation
                ? " Human escalation is required."
                : " No human escalation is required.");
    }

    private boolean containsAny(
            String text,
            String... terms) {

        for (String term : terms) {
            if (text.contains(term)) {
                return true;
            }
        }

        return false;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}