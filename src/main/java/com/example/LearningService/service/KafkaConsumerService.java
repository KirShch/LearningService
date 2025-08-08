package com.example.LearningService.service;

import com.example.LearningService.dto.KafkaTestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "test-topic1")
    public void listen(String message) throws JsonProcessingException {
        try{
            KafkaTestDto kafkaTestDto = objectMapper.readValue(message, KafkaTestDto.class);
            System.out.println("Received message: " + kafkaTestDto);
            objectMapper.writeValueAsString(kafkaTestDto);
            kafkaTemplate.send("test-topic1", "Message " + kafkaTestDto.getMessage() + " is received");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
