package org.example.searchservice3.repository;

import org.example.searchservice3.entities.IncomingMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<IncomingMessage, String> {
}
