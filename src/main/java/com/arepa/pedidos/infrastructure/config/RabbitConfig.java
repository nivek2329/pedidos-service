package com.arepa.pedidos.infrastructure.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "arepas.eventos";

    @Bean
    public TopicExchange arepasExchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public MessageConverter jsonConverter() { return new JacksonJsonMessageConverter(); }
}
