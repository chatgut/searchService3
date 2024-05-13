package org.example.searchservice3.repository;

import org.example.searchservice3.entities.MessageDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<MessageDocument, String> {

    List<MessageDocument> findAll();

    //search on words
    List<MessageDocument> findByMessageContaining(String text);

//    allows for spelling errors, multiple results returned with spaces in between words
    @Query("{\"match\": {\"message\":{\"query\":\"?0\",\"fuzziness\":\"AUTO\"}}}")
    List<MessageDocument> findByMessageContainingWithSpellingErrors(String text);

    //search on whole phrases
//    @Query("{\"match_phrase\":{\"message\":\"?0\"}}")
//    List<MessageDocument> findByMessageContainingWithSpellingErrors(String text);


    //find as you type
    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"message\"],\"type\":\"phrase_prefix\"}}")
    List<MessageDocument> findByMessageAsYouType(String text);

}
