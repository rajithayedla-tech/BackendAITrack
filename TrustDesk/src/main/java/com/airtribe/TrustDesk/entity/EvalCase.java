package com.airtribe.TrustDesk.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "eval_cases")
public class EvalCase {

    @Id
    @Column(name = "case_id", nullable = false)
    private String caseId;

    @Column(name = "ticket_id", nullable = false)
    private String ticketId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String input;

    @Embedded
    private EvalExpected expected;

    public EvalCase() {
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public EvalExpected getExpected() {
        return expected;
    }

    public void setExpected(EvalExpected expected) {
        this.expected = expected;
    }
}