package com.markur.searchengine;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchEngineTest {

    @Test
    public void shouldFindBrown() {
        // given
        SearchEngine searchEngine = new SearchEngine(new DocumentIdProvider());
        searchEngine.addDocument("the brown fox jumped over the brown dog");
        searchEngine.addDocument("the lazy brown dog sat in the corner");
        searchEngine.addDocument("the red fox bit the lazy dog");

        // when
        List<SingleTermSearchDto> result = searchEngine.searchSingleTerm("brown");

        // then
        assertThat(result).hasSize(2);

        SingleTermSearchDto singleTermSearchDto1 = result.get(0);
        assertThat(singleTermSearchDto1.getDocumentId()).isEqualTo("1");
        assertThat(singleTermSearchDto1.getDocument()).isEqualTo("the brown fox jumped over the brown dog");

        SingleTermSearchDto singleTermSearchDto2 = result.get(1);
        assertThat(singleTermSearchDto2.getDocumentId()).isEqualTo("2");
        assertThat(singleTermSearchDto2.getDocument()).isEqualTo("the lazy brown dog sat in the corner");
    }

    @Test
    public void shouldFindFix() {
        // given
        SearchEngine searchEngine = new SearchEngine(new DocumentIdProvider());
        searchEngine.addDocument("the brown fox jumped over the brown dog");
        searchEngine.addDocument("the lazy brown dog sat in the corner");
        searchEngine.addDocument("the red fox bit the lazy dog");

        // when
        List<SingleTermSearchDto> result = searchEngine.searchSingleTerm("fox");

        // then
        assertThat(result).hasSize(2);


        SingleTermSearchDto singleTermSearchDto1 = result.get(0);
        assertThat(singleTermSearchDto1.getDocumentId()).isEqualTo("3");
        assertThat(singleTermSearchDto1.getDocument()).isEqualTo("the red fox bit the lazy dog");

        SingleTermSearchDto singleTermSearchDto2 = result.get(1);
        assertThat(singleTermSearchDto2.getDocumentId()).isEqualTo("1");
        assertThat(singleTermSearchDto2.getDocument()).isEqualTo("the brown fox jumped over the brown dog");
    }
}
