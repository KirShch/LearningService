package com.example.LearningService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final RabbitProperties rabbitProperties;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitProperties.getExchangeName());
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(rabbitProperties.getQueueName())
                .withArgument("x-dead-letter-exchange", rabbitProperties.getExchangeName())
                .withArgument("x-dead-letter-routing-key", rabbitProperties.getDqlName())
                .build();
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(rabbitProperties.getDqlName()).build();
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(rabbitProperties.getRoutingKey());
    }

    @Bean
    Binding dlqBinding(Queue dlq, TopicExchange exchange) {
        return BindingBuilder.bind(dlq)
                .to(exchange)
                .with(rabbitProperties.getDqlName());
    }
}
