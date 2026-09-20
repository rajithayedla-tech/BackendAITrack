package com.airtribe.TrustDesk.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tool_actions")
public class ToolAction {

    @Id
    @Column(name = "tool_name")
    private String toolName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "requires_human_approval")
    private boolean requiresHumanApproval;

    @ElementCollection
    @CollectionTable(
            name = "tool_action_allowed_categories",
            joinColumns = @JoinColumn(name = "tool_name")
    )
    @Column(name = "category")
    private List<String> allowedCategories;

    @ElementCollection
    @CollectionTable(
            name = "tool_action_required_fields",
            joinColumns = @JoinColumn(name = "tool_name")
    )
    @Column(name = "field_name")
    private List<String> requiredFields;

    @Column(name = "max_amount_inr")
    private BigDecimal maxAmountInr;

    public ToolAction() {
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public boolean isRequiresHumanApproval() {
        return requiresHumanApproval;
    }

    public void setRequiresHumanApproval(boolean requiresHumanApproval) {
        this.requiresHumanApproval = requiresHumanApproval;
    }

    public List<String> getAllowedCategories() {
        return allowedCategories;
    }

    public void setAllowedCategories(List<String> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public BigDecimal getMaxAmountInr() {
        return maxAmountInr;
    }

    public void setMaxAmountInr(BigDecimal maxAmountInr) {
        this.maxAmountInr = maxAmountInr;
    }
}