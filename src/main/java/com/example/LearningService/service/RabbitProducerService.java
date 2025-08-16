package com.example.LearningService.service;

import com.example.LearningService.config.RabbitProperties;
import com.example.LearningService.dto.EnrollmentOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public void sendEnrollmentCreatedEvent(EnrollmentOutDto outDto){
        rabbitTemplate.convertAndSend(
                rabbitProperties.getExchangeName(),
                rabbitProperties.getRoutingKey(),
                outDto
        );
        log.info("Enrollment out DTO is sent to " + rabbitProperties.getExchangeName() +
                "/n DTO: " + outDto);
    }
}
