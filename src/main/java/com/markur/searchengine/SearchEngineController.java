package com.markur.searchengine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchEngineController {

    private final SearchEngine searchEngine;

    SearchEngineController(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @PostMapping("/documents")
    @ResponseBody
    ResponseEntity<?> addDocument(@RequestBody List<String> documents) {
        List<DocumentDto> documentDtoList = documents
                .stream()
                .map(searchEngine::addDocument)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documentDtoList);
    }

    @GetMapping("/search")
    @ResponseBody
    ResponseEntity<?> searchSingleTerm(@RequestParam String term) {
        return ResponseEntity.ok(searchEngine.searchSingleTerm(term));
    }

    @GetMapping("/documents")
    @ResponseBody
    ResponseEntity<?> getDocuments() {
        return ResponseEntity.ok(searchEngine.getDocuments());
    }

    @GetMapping("/indexes")
    @ResponseBody
    ResponseEntity<?> getIndexes() {
        return ResponseEntity.ok(searchEngine.getIndexes());
    }
}
