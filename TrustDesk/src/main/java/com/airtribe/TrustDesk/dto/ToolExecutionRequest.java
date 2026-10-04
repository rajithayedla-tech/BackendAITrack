package com.airtribe.TrustDesk.dto;

import java.util.Map;

public class ToolExecutionRequest {

    private String ticketId;
    private String toolName;
    private Map<String, Object> parameters;

    public ToolExecutionRequest() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}