package com.airtribe.TrustDesk.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "order_id")
    private String orderId;

    private String channel;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at")
    private String createdAt;

    private String status;

    @Column(name = "expected_category")
    private String expectedCategory;

    @Column(name = "expected_priority")
    private String expectedPriority;

    @Column(name = "expected_sentiment")
    private String expectedSentiment;

    @Column(name = "expected_escalation")
    private boolean expectedEscalation;

    @ElementCollection
    @CollectionTable(
            name = "ticket_expected_actions",
            joinColumns = @JoinColumn(name = "ticket_id")
    )
    @Column(name = "action")
    private List<String> expectedActions;

    public Ticket() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpectedCategory() {
        return expectedCategory;
    }

    public void setExpectedCategory(String expectedCategory) {
        this.expectedCategory = expectedCategory;
    }

    public String getExpectedPriority() {
        return expectedPriority;
    }

    public void setExpectedPriority(String expectedPriority) {
        this.expectedPriority = expectedPriority;
    }

    public String getExpectedSentiment() {
        return expectedSentiment;
    }

    public void setExpectedSentiment(String expectedSentiment) {
        this.expectedSentiment = expectedSentiment;
    }

    public boolean isExpectedEscalation() {
        return expectedEscalation;
    }

    public void setExpectedEscalation(boolean expectedEscalation) {
        this.expectedEscalation = expectedEscalation;
    }

    public List<String> getExpectedActions() {
        return expectedActions;
    }

    public void setExpectedActions(List<String> expectedActions) {
        this.expectedActions = expectedActions;
    }
}