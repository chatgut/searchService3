package org.example.searchservice3.repository;

import org.example.searchservice3.entities.MessageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<MessageDocument, String> {

    List<MessageDocument> findAll();
}
