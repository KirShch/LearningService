package com.example.LearningService.service;

import com.example.LearningService.config.KafkaProperties;
import com.example.LearningService.dto.CourseKafkaStatus;
import com.example.LearningService.dto.CourseOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, CourseOutDto> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void sendOutMessage(String key, CourseOutDto outDto, Long courseId){
        outDto.getPayload().setLsCourseId(courseId);
        outDto.getPayload().setStatus(CourseKafkaStatus.IMPORTED);
        kafkaTemplate.send(kafkaProperties.getTopicOut(), key, outDto);
        log.info("message is sent to " + kafkaProperties.getTopicOut() +
                "\nkey: " + key +
                "\nDTO: " + outDto);
    }

    public void sendOutMessageWithValidationErrors(String key, CourseOutDto outDto, List<String> validationErrors){
        outDto.getPayload().setErrors(validationErrors);
        outDto.getPayload().setStatus(CourseKafkaStatus.DENIED);
        kafkaTemplate.send(kafkaProperties.getTopicOut(), key, outDto);
        log.info("message is sent (with validation errors) to " + kafkaProperties.getTopicOut() +
                "\nkey: " + key +
                "\nDTO: " + outDto);
    }

    public void sendOutMessageWithException(String key, CourseOutDto outDto, String exceptionMessage){
        outDto.getPayload().setErrors(List.of(exceptionMessage));
        outDto.getPayload().setStatus(CourseKafkaStatus.DENIED);
        kafkaTemplate.send(kafkaProperties.getTopicOutDlq(), key, outDto);
        log.info("message is sent (with exception) to " + kafkaProperties.getTopicOutDlq() +
                "\nkey: " + key +
                "\nDTO: " + outDto);
    }
}
