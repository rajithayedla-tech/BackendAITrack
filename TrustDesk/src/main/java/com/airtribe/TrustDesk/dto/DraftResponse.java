package com.airtribe.TrustDesk.dto;

import java.util.List;

public class DraftResponse {

    private String ticketId;
    private String category;
    private String priority;
    private boolean escalationRequired;
    private String draft;
    private List<String> citationIds;

    public DraftResponse() {
    }

    public DraftResponse(
            String ticketId,
            String category,
            String priority,
            boolean escalationRequired,
            String draft,
            List<String> citationIds) {

        this.ticketId = ticketId;
        this.category = category;
        this.priority = priority;
        this.escalationRequired = escalationRequired;
        this.draft = draft;
        this.citationIds = citationIds;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isEscalationRequired() {
        return escalationRequired;
    }

    public void setEscalationRequired(boolean escalationRequired) {
        this.escalationRequired = escalationRequired;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public List<String> getCitationIds() {
        return citationIds;
    }

    public void setCitationIds(List<String> citationIds) {
        this.citationIds = citationIds;
    }
}