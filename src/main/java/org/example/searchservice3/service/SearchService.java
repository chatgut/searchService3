package org.example.searchservice3.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SearchService {

    @Autowired
    ElasticsearchClient elasticsearchClient;

    @Autowired
    RestClient restClient;


    private final ElasticsearchOperations elasticsearchOperations;
    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository, ElasticsearchOperations elasticsearchOperations) {
        this.searchRepository = searchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
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


    public String findMessageById(String id) {

        MessageDocument document = elasticsearchOperations.get(Objects.requireNonNull(elasticsearchOperations.convertId(id)), MessageDocument.class);

        if (document != null)
            return document.getMessage();
        else
            return null;

    }

    public List<MessageDocument> findAllMessages() {

        return searchRepository.findAll();
    }

    public List<MessageDocument> search(String text) {

        return searchRepository.findByMessageContainingIgnoreCase(text);
    }

    public List<MessageDocument> searchWithSpellingErrors(String text, String userID) {

        return searchRepository.findByMessageContainingWithSpellingErrorsAndToOrFromAndUserId(text, userID);
    }

    public List<MessageDocument> findByMessageAsYouType(String text) {
        return searchRepository.findByMessageAsYouType(text);
    }
}
