package org.example.searchservice3.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("messages");
    }
    @Bean
    Binding bindMessages(FanoutExchange fanoutExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    Queue queue() {
        return new Queue("messagesForElasticsearch");
    }

    @Bean
    ApplicationRunner runner (ConnectionFactory connectionFactory) {
        return args -> connectionFactory.createConnection().close();
        }

}
