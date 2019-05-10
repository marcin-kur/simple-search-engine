package com.markur.searchengine;

class DocumentDto {
    private final String documentId;
    private final String document;

    DocumentDto(String documentId, String document) {
        this.documentId = documentId;
        this.document = document;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocument() {
        return document;
    }
}
