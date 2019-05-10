package com.markur.searchengine;

import java.math.BigDecimal;

class SingleTermSearchDto {
    private final String documentId;
    private final String document;
    private final BigDecimal score;

    SingleTermSearchDto(String documentId, String document, BigDecimal score) {
        this.documentId = documentId;
        this.document = document;
        this.score = score;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocument() {
        return document;
    }

    public BigDecimal getScore() {
        return score;
    }
}
