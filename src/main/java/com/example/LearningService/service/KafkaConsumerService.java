package com.example.LearningService.service;

import com.example.LearningService.config.KafkaProperties;
import com.example.LearningService.dto.*;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.CourseMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final KafkaProducerService kafkaProducerService;
    private final UserService userService;
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final Validator validator;
    private final KafkaProperties kafkaProperties;

    public final String kafkaIn = "external.courses.inbound";

    @KafkaListener(topics = kafkaIn)
    public void handleCourseCreation(ConsumerRecord<String, CourseInDto> record) throws JsonProcessingException {
        log.info("Consumer record been received (key and courseInDto from topic external.courses.inbound)");
        processCourseCreation(record.key(), record.value());
    }

    public void processCourseCreation(String key, CourseInDto inDto){
        CourseOutDto initOutDto = initCourseOutDto(inDto);
        try{
            CourseDto courseDto = initCourseDto(inDto);
            List<String> validationErrors = getValidationErrors(courseDto);
            if (!validationErrors.isEmpty())
                kafkaProducerService.sendOutMessageWithValidationErrors(key, initOutDto, validationErrors);
            else {
                courseDto.setStatus(CourseStatus.PUBLISHED);
                Long courseId = courseService.createCourse(courseDto).getId();
                kafkaProducerService.sendOutMessage(key, initOutDto, courseId);
            }
        }
        catch (Exception e){
            kafkaProducerService.sendOutMessageWithException(key, initOutDto, e.getMessage());
        }
    }

    private List<String> getValidationErrors(CourseDto courseDto){
        BindingResult bindingResult = new BeanPropertyBindingResult(courseDto, "courseDto");
        validator.validate(courseDto, bindingResult);
        return bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

    private CourseDto initCourseDto(CourseInDto inDto){
        CourseDto courseDto = courseMapper.toDto(inDto.getPayload());
        User author = userService.findByEmail(inDto.getPayload().getAuthorEmail());
        courseDto.setAuthorId(author.getId());
        courseDto.setStatus(CourseStatus.PUBLISHED);
        return courseDto;
    }

    public CourseOutDto initCourseOutDto(CourseInDto inDto){
        CourseOutDto outDto = new CourseOutDto();
        outDto.setEventId(inDto.getEventId());
        outDto.setSystemId(kafkaProperties.getSystemId());
        outDto.setTimestamp(Instant.now());
        outDto.setPayload(new PayloadOutDto());
        outDto.getPayload().setExternalCourseId(inDto.getPayload().getExternalCourseId());
        outDto.getPayload().setAuthorEmail(inDto.getPayload().getAuthorEmail());
        return  outDto;
    }
}
