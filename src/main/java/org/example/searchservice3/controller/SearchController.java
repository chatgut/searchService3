package org.example.searchservice3.controller;


import org.example.searchservice3.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {


    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/message/{id}")
    public String message(@PathVariable String id) {

        return searchService.findMessageById(id);
    }

}
