package com.markur.searchengine;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
class DocumentIdProvider {

    private final AtomicLong idSeq;

    DocumentIdProvider() {
        this.idSeq = new AtomicLong(1);
    }

    String getId() {
        return String.valueOf(idSeq.getAndIncrement());
    }
}
