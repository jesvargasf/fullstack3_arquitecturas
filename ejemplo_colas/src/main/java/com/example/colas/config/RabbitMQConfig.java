package com.example.colas.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // === UNA SOLA COLA SIMPLE ===
    public static final String COLA_MENSAJES = "mensajes.simple";
    public static final String EXCHANGE_SIMPLE = "exchange.simple";

    @Bean
    public Queue colaSimple() {
        return QueueBuilder.durable(COLA_MENSAJES).build();
    }

    @Bean
    public DirectExchange exchangeSimple() {
        return new DirectExchange(EXCHANGE_SIMPLE);
    }

    @Bean
    public Binding bindingSimple() {
        return BindingBuilder
                .bind(colaSimple())
                .to(exchangeSimple())
                .with("routing.key.simple");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
