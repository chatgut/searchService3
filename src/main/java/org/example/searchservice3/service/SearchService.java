package org.example.searchservice3.service;

import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.repository.SearchRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


    private final SearchRepository searchRepository;
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

//    public void indexMessage(String id, String fromUser, String toUser, String messageText, String date) {
//        System.out.println("Indexing message: " + id + " " + fromUser + " " + toUser + " " + messageText + " " + date);
//    }

    public void indexMessage(String id, String fromUser, String toUser, String messageText, String date) {
        MessageDocument document = new MessageDocument();
        document.setId(id);
        document.setFrom(fromUser);
        document.setTo(toUser);
        document.setMessage(messageText);
        document.setDate(date);
        searchRepository.save(document);
        //todo: save the document to elasticsearch. use the imperative config??
        System.out.println("Indexing message: " + id + " " + fromUser + " " + toUser + " " + messageText + " " + date);
    }
}
