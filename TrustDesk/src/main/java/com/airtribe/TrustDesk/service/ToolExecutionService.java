package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.dto.ToolExecutionRequest;
import com.airtribe.TrustDesk.dto.ToolValidationResult;
import com.airtribe.TrustDesk.dto.TriageResult;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.entity.ToolAction;
import com.airtribe.TrustDesk.repository.TicketRepository;
import com.airtribe.TrustDesk.repository.ToolActionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ToolExecutionService {

    private final ToolActionRepository toolActionRepository;
    private final TicketRepository ticketRepository;
    private final TriageService triageService;

    public ToolExecutionService(
            ToolActionRepository toolActionRepository,
            TicketRepository ticketRepository,
            TriageService triageService) {

        this.toolActionRepository = toolActionRepository;
        this.ticketRepository = ticketRepository;
        this.triageService = triageService;
    }

    public ToolValidationResult validate(ToolExecutionRequest request) {

        if (request == null) {
            return invalid("Tool request cannot be null");
        }

        if (request.getTicketId() == null || request.getTicketId().isBlank()) {
            return invalid("ticketId is required");
        }

        if (request.getToolName() == null || request.getToolName().isBlank()) {
            return invalid("toolName is required");
        }

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElse(null);

        if (ticket == null) {
            return invalid("Ticket not found: " + request.getTicketId());
        }

        ToolAction toolAction = toolActionRepository
                .findById(request.getToolName())
                .orElse(null);

        if (toolAction == null) {
            return invalid("Unknown tool action: " + request.getToolName());
        }

        String category = getTicketCategory(ticket);

        if (!isCategoryAllowed(toolAction, category)) {
            return invalid(
                    "Tool '" + toolAction.getToolName()
                            + "' is not allowed for category '" + category + "'"
            );
        }

        Map<String, Object> parameters = request.getParameters();

        List<String> missingFields =
                findMissingFields(toolAction.getRequiredFields(), parameters);

        if (!missingFields.isEmpty()) {
            return new ToolValidationResult(
                    false,
                    toolAction.isRequiresHumanApproval(),
                    "Required fields are missing",
                    missingFields
            );
        }

        if (!validateAmount(toolAction, parameters)) {
            return invalid(
                    "Requested amount exceeds the maximum allowed amount for this tool"
            );
        }

        return new ToolValidationResult(
                true,
                toolAction.isRequiresHumanApproval(),
                toolAction.isRequiresHumanApproval()
                        ? "Tool is valid but requires human approval"
                        : "Tool is valid and can be executed",
                List.of()
        );
    }

    private String getTicketCategory(Ticket ticket) {
        TriageResult triage = triageService.triage(ticket.getTicketId());
        return triage.getCategory();
    }

    private boolean isCategoryAllowed(
            ToolAction toolAction,
            String category) {

        if (toolAction.getAllowedCategories() == null) {
            return false;
        }

        return toolAction.getAllowedCategories()
                .stream()
                .anyMatch(allowed ->
                        allowed.equalsIgnoreCase(category));
    }

    private List<String> findMissingFields(
            List<String> requiredFields,
            Map<String, Object> parameters) {

        List<String> missingFields = new ArrayList<>();

        if (requiredFields == null) {
            return missingFields;
        }

        for (String field : requiredFields) {

            if (parameters == null
                    || !parameters.containsKey(field)
                    || parameters.get(field) == null
                    || parameters.get(field).toString().isBlank()) {

                missingFields.add(field);
            }
        }

        return missingFields;
    }

    private boolean validateAmount(
            ToolAction toolAction,
            Map<String, Object> parameters) {

        if (toolAction.getMaxAmountInr() == null) {
            return true;
        }

        if (parameters == null || !parameters.containsKey("amount")) {
            return false;
        }

        Object amountValue = parameters.get("amount");

        try {
            BigDecimal amount =
                    new BigDecimal(amountValue.toString());

            return amount.compareTo(toolAction.getMaxAmountInr()) <= 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private ToolValidationResult invalid(String message) {

        return new ToolValidationResult(
                false,
                false,
                message,
                List.of()
        );
    }
}