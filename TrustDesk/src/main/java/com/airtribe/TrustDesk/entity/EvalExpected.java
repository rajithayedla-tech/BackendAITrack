package com.airtribe.TrustDesk.entity;

import jakarta.persistence.*;

import java.util.List;

@Embeddable
public class EvalExpected {

    private String category;

    private String priority;

    @ElementCollection
    @CollectionTable(
            name = "eval_must_cite_doc_ids",
            joinColumns = @JoinColumn(name = "case_id")
    )
    @Column(name = "document_id")
    private List<String> mustCiteDocIds;

    @ElementCollection
    @CollectionTable(
            name = "eval_allowed_actions",
            joinColumns = @JoinColumn(name = "case_id")
    )
    @Column(name = "action")
    private List<String> allowedActions;

    @ElementCollection
    @CollectionTable(
            name = "eval_disallowed_actions",
            joinColumns = @JoinColumn(name = "case_id")
    )
    @Column(name = "action")
    private List<String> disallowedActions;

    @Column(name = "should_escalate")
    private boolean shouldEscalate;

    @ElementCollection
    @CollectionTable(
            name = "eval_answer_requirements",
            joinColumns = @JoinColumn(name = "case_id")
    )
    @Column(name = "requirement")
    private List<String> answerRequirements;

    public EvalExpected() {
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

    public List<String> getMustCiteDocIds() {
        return mustCiteDocIds;
    }

    public void setMustCiteDocIds(List<String> mustCiteDocIds) {
        this.mustCiteDocIds = mustCiteDocIds;
    }

    public List<String> getAllowedActions() {
        return allowedActions;
    }

    public void setAllowedActions(List<String> allowedActions) {
        this.allowedActions = allowedActions;
    }

    public List<String> getDisallowedActions() {
        return disallowedActions;
    }

    public void setDisallowedActions(List<String> disallowedActions) {
        this.disallowedActions = disallowedActions;
    }

    public boolean isShouldEscalate() {
        return shouldEscalate;
    }

    public void setShouldEscalate(boolean shouldEscalate) {
        this.shouldEscalate = shouldEscalate;
    }

    public List<String> getAnswerRequirements() {
        return answerRequirements;
    }

    public void setAnswerRequirements(List<String> answerRequirements) {
        this.answerRequirements = answerRequirements;
    }
}