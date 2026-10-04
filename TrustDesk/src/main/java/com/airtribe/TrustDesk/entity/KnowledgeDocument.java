package com.airtribe.TrustDesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "knowledge_documents")
public class KnowledgeDocument {

    @Id
    @Column(name = "document_id", nullable = false)
    private String documentId;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String audience;

    @Column(nullable = false)
    private String version;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public KnowledgeDocument() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}