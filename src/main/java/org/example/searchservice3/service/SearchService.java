package org.example.searchservice3.service;

import org.springframework.stereotype.Service;

@Service
public class SearchService {
    public void indexMessage(String text) {
        System.out.println("Indexing message: " + text);
    }
}
