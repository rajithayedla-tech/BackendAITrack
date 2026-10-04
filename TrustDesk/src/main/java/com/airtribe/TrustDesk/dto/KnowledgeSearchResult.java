package com.airtribe.TrustDesk.dto;

public class KnowledgeSearchResult {

    private String documentId;
    private String title;
    private String snippet;
    private double score;

    public KnowledgeSearchResult() {
    }

    public KnowledgeSearchResult(
            String documentId,
            String title,
            String snippet,
            double score) {

        this.documentId = documentId;
        this.title = title;
        this.snippet = snippet;
        this.score = score;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}