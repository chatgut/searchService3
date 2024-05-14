package org.example.searchservice3.controller;


import org.example.searchservice3.entities.MessageDocument;
import org.example.searchservice3.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {


    private final SearchService searchService;

     public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{text}")
    public List<MessageDocument> search(@RequestHeader (name = "UserID") String userID, @PathVariable String text) {
        return searchService.search(text, userID);
    }


    @GetMapping("/find/{text}")
    public List<MessageDocument> findAsYouType(@PathVariable String text) {
        return searchService.findByMessageAsYouType(text);
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
