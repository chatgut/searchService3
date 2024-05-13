package org.example.searchservice3.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {


    private final SearchService searchService;

     public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/searchExact/{text}")
    public List<MessageDocument> searchExact(@PathVariable String text) {

        return searchService.search(text);
    }

    @GetMapping("/search/{text}")
    public List<MessageDocument> search(@PathVariable String text) {
        return searchService.findByMessageContainingWithSpellingErrors(text);
    }

    @GetMapping("/message/{id}")
    public String message(@PathVariable String id) {

        return searchService.findMessageById(id);
    }

    @GetMapping("/all")
    public String all() {
        return searchService.findAllMessages().toString();
    }


}
