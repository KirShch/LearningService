package com.example.LearningService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {
    private String bootstrapServers;
    private String groupId;
    private String systemId;
    private String topicIn;
    private String topicOut;
    private String topicOutDlq;
}
