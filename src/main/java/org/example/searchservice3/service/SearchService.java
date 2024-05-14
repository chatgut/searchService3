package org.example.searchservice3.service;

import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.repository.SearchRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SearchService {

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


    public List<MessageDocument> search(String text, String userID) {


        List<MessageDocument> messages = searchRepository.findByMessageContainingWithSpellingErrorsAndToOrFromAndUserId(text, userID);

        return messages.stream()
                .filter(message -> userID.equals(message.getFrom()) || userID.equals(message.getTo()))
                .collect(Collectors.toList());

    }

    public List<MessageDocument> findByMessageAsYouType(String text, String userID) {
        List<MessageDocument> messages = searchRepository.findByMessageAsYouType(text);
        return messages.stream()
                .filter(message -> userID.equals(message.getFrom()) || userID.equals(message.getTo()))
                .collect(Collectors.toList());
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

    public List<MessageDocument> getMessagesBetweenUsers(String text, String userID, String otherUserID) {
        List<MessageDocument> messages = searchRepository.findByMessageContainingWithSpellingErrorsAndToOrFromAndUserId(text, userID, otherUserID);

        return messages.stream()
                .filter(message -> userID.equals(message.getFrom()) || userID.equals(message.getTo()))
                .collect(Collectors.toList());

    }
}
