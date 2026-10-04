package com.airtribe.TrustDesk.dto;

import java.util.List;

public class AiDraft {

    private String draft;

    private List<String> citationIds;

    private boolean escalationRequired;

    public AiDraft() {
    }

    public AiDraft(
            String draft,
            List<String> citationIds,
            boolean escalationRequired) {

        this.draft = draft;
        this.citationIds = citationIds;
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

    public boolean isEscalationRequired() {
        return escalationRequired;
    }

    public void setEscalationRequired(boolean escalationRequired) {
        this.escalationRequired = escalationRequired;
    }
}