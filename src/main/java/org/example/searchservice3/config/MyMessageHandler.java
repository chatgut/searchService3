package org.example.searchservice3.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.searchservice3.service.SearchService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Service
public class MyMessageHandler implements MessageListener {

    private final SearchService searchService;
    private final AmqpAdmin amqpAdmin;
    private final FanoutExchange fanoutExchange;

    public MyMessageHandler(SearchService searchService, AmqpAdmin amqpAdmin, FanoutExchange fanoutExchange) {
        this.searchService = searchService;
        this.amqpAdmin = amqpAdmin;
        this.fanoutExchange = fanoutExchange;
    }


    @Override
    @RabbitListener(queues = "messages")
    public void onMessage(Message message) {
        try {

            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

            JsonNode messageJson = new ObjectMapper().readTree(messageBody);

            String id = messageJson.get("_id").get("$oid").asText();
            String fromUser = messageJson.get("from").asText();
            String toUser = messageJson.get("to").asText();
            String messageText = messageJson.get("message").asText();
            String date = messageJson.get("date").asText();
            ZonedDateTime dateTime = ZonedDateTime.parse(date);

            searchService.indexMessage(id, fromUser, toUser, messageText, dateTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}