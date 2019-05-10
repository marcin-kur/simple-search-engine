package com.markur.searchengine;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
class SearchEngine {

    private final DocumentIdProvider documentIdProvider;
    private final Map<String, String> documents;
    private final Map<String, Map<String, BigDecimal>> invertedIndex;

    SearchEngine(DocumentIdProvider documentIdProvider) {
        this.documentIdProvider = documentIdProvider;
        this.documents = new HashMap<>();
        this.invertedIndex = new TreeMap<>();
    }

    DocumentDto addDocument(String document) {
        Map<String, BigDecimal> documentTerms = parseDocument(document);
        String documentId = documentIdProvider.getId();
        documents.put(documentId, document);
        documentTerms.forEach(putTermInInvertedIndex(documentId));
        return new DocumentDto(documentId, document);
    }

    List<DocumentDto> getDocuments() {
        return documents.keySet()
                .stream()
                .map(documentId -> new DocumentDto(documentId, documents.get(documentId)))
                .collect(Collectors.toList());
    }

    List<SingleTermSearchDto> searchSingleTerm(String term) {
        return Optional.ofNullable(invertedIndex.get(term))
                .filter(termFrequencyMap -> termFrequencyMap.size() > 0)
                .map(this::searchSingleTerm)
                .orElseGet(Collections::emptyList);
    }

    List<IndexDto> getIndexes() {
        return invertedIndex
                .keySet()
                .stream()
                .map(term -> new IndexDto(term, invertedIndex.get(term)))
                .collect(Collectors.toList());
    }

    private BiConsumer<String, BigDecimal> putTermInInvertedIndex(String documentId) {
        return (term, frequency) -> {
            invertedIndex.computeIfAbsent(term, (x -> new HashMap<>()))
                    .put(documentId, frequency);
        };
    }

    private Map<String, BigDecimal> parseDocument(String document) {
        String[] terms = document.split(" ");
        Map<String, Long> termsCount = computeTermsCountInDocument(terms);
        return computeTermsFrequency(BigDecimal.valueOf(terms.length), termsCount);
    }

    private Map<String, Long> computeTermsCountInDocument(String[] terms) {
        Map<String, Long> termsCount = new HashMap<>();

        for (String word : terms) {
            termsCount.put(word, termsCount.getOrDefault(word, 0L) + 1);
        }

        return termsCount;
    }

    private Map<String, BigDecimal> computeTermsFrequency(BigDecimal documentLength, Map<String, Long> termsCount) {
        Map<String, BigDecimal> wordsFrequency = new HashMap<>();

        for (String term : termsCount.keySet()) {
            BigDecimal termFrequency = BigDecimal.valueOf(termsCount.get(term))
                    .divide(documentLength, 16, BigDecimal.ROUND_CEILING);
            wordsFrequency.put(term, termFrequency);

        }
        return wordsFrequency;
    }

    private List<SingleTermSearchDto> searchSingleTerm(Map<String, BigDecimal> termFrequencyMap) {
        List<SingleTermSearchDto> result = new ArrayList<>();

        BigDecimal inverseDocumentFrequency = computeInverseDocumentFrequency(documents.size(), termFrequencyMap.size());

        termFrequencyMap
                .forEach((documentId, frequency) -> {
                    BigDecimal score = frequency.multiply(inverseDocumentFrequency);
                    result.add(new SingleTermSearchDto(documentId, documents.get(documentId), score));
                });

        result.sort((r1, r2) -> r2.getScore().compareTo(r1.getScore()));

        return result;
    }

    private BigDecimal computeInverseDocumentFrequency(int totalNumberOfDocuments, int numberOfDocumentsWithTerm) {
        double result = Math.log((float) totalNumberOfDocuments / numberOfDocumentsWithTerm);
        return BigDecimal.valueOf(result);
    }
}
