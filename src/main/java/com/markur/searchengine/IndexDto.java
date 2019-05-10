package com.markur.searchengine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class IndexDto {
    private final String term;
    private final List<OccurrenceDto> occurrences;

    IndexDto(String term, Map<String, BigDecimal> occurrences) {
        this.term = term;
        this.occurrences = occurrences.keySet()
                .stream()
                .map(documentId -> new OccurrenceDto(documentId, occurrences.get(documentId)))
                .collect(Collectors.toList());
    }

    public String getTerm() {
        return term;
    }

    public List<OccurrenceDto> getOccurrences() {
        return occurrences;
    }

    static class OccurrenceDto {
        private final String documentId;
        private final BigDecimal termFrequency;

        OccurrenceDto(String documentId, BigDecimal termFrequency) {
            this.documentId = documentId;
            this.termFrequency = termFrequency;
        }

        public String getDocumentId() {
            return documentId;
        }

        public BigDecimal getTermFrequency() {
            return termFrequency;
        }
    }

}
