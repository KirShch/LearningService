package com.example.LearningService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbit")
@Getter
@Setter
public class RabbitProperties {
    private String exchangeName;
    private String routingKey;
    private String queueName;
    private String dqlName;
}
