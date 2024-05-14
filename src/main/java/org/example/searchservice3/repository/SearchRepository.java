package org.example.searchservice3.repository;

import org.example.searchservice3.entities.MessageDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<MessageDocument, String> {

    List<MessageDocument> findAll();

    //    allows for spelling errors, multiple results returned with AND instead of OR
    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"to\": \"?1\"}}, {\"match\": {\"from\": \"?1\"}}]}}, {\"match\": {\"message\": {\"query\": \"?0\", \"operator\": \"and\", \"fuzziness\": \"AUTO\"}}}]}}")
    List<MessageDocument> findByMessageContainingWithSpellingErrorsAndToOrFromAndUserId(String text, String userID);

    //find as you type
    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"message\"],\"type\":\"phrase_prefix\"}}")
    List<MessageDocument> findByMessageAsYouType(String text);

}
