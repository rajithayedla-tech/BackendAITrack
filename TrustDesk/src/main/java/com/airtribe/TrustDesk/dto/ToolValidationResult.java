package com.airtribe.TrustDesk.dto;

import java.util.List;

public class ToolValidationResult {

    private boolean allowed;
    private boolean requiresHumanApproval;
    private String message;
    private List<String> missingFields;

    public ToolValidationResult() {
    }

    public ToolValidationResult(
            boolean allowed,
            boolean requiresHumanApproval,
            String message,
            List<String> missingFields) {

        this.allowed = allowed;
        this.requiresHumanApproval = requiresHumanApproval;
        this.message = message;
        this.missingFields = missingFields;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isRequiresHumanApproval() {
        return requiresHumanApproval;
    }

    public void setRequiresHumanApproval(boolean requiresHumanApproval) {
        this.requiresHumanApproval = requiresHumanApproval;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(List<String> missingFields) {
        this.missingFields = missingFields;
    }
}