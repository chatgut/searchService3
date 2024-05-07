package org.example.searchservice3.service;

import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SearchService {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    private final SearchRepository searchRepository;

    // could replace @Autowired with @Qualifier("elasticsearchOperations") ElasticsearchOperations elasticsearchOperations in the constructor
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }


    public void indexMessage(String id, String fromUser, String toUser, String messageText, String date) {
        MessageDocument document = new MessageDocument();
        document.setId(id);
        document.setFrom(fromUser);
        document.setTo(toUser);
        document.setMessage(messageText);
        document.setDate(date);
        //elasticsearchOperations.save(document);
        searchRepository.save(document);
        System.out.println("Indexing message: " + id + " " + fromUser + " " + toUser + " " + messageText + " " + date);
    }


    public String searchMessage(String id) {

        MessageDocument document = elasticsearchOperations.get(Objects.requireNonNull(elasticsearchOperations.convertId(id)), MessageDocument.class);

        if (document != null) {
            return document.getMessage();
        } else {
            return null;
        }
    }
}
