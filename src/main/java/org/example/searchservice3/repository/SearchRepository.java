package org.example.searchservice3.repository;

import org.example.searchservice3.entities.MessageDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<MessageDocument, String> {

    List<MessageDocument> findAll();

    List<MessageDocument> findByMessageContaining(String text);

    @Query("{\"match\": {\"message\":{\"query\":\"?0\",\"fuzziness\":\"AUTO\"}}}")
    List<MessageDocument> findByMessageContainingWithSpellingErrors(String text);

}
