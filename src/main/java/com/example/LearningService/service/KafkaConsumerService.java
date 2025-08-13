package com.example.LearningService.service;

import com.example.LearningService.dto.*;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.CourseMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final KafkaTemplate<String, CourseOutDto> kafkaTemplate;
    private final UserService userService;
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final Validator validator;

    private static final AtomicLong eventId = new AtomicLong(0);
    private static final String systemId = "ls-core";

    @KafkaListener(topics = "external.courses.inbound")
    public void handleCourseCreation(ConsumerRecord<String, CourseInDto> record) throws JsonProcessingException {
        processCourseCreation(record.key(), record.value());
    }

    private void processCourseCreation(String key, CourseInDto inDto){
        CourseOutDto initOutDto = initCourseOutDto(inDto);
        try{
            CourseDto courseDto = initCourseDto(inDto);
            List<String> validationErrors = getValidationErrors(courseDto);
            if (!validationErrors.isEmpty())
                sendOutMessageWithValidationErrors(key, initOutDto, validationErrors);
            else {
                courseDto.setStatus(CourseStatus.PUBLISHED);
                Long courseId = courseService.createCourse(courseDto).getId();
                sendOutMessage(key, initOutDto, courseId);
            }
        }
        catch (Exception e){
            sendOutMessageWithException(key, initOutDto, e.getMessage());
        }
    }

    public void sendOutMessage(String key, CourseOutDto initOutDto, Long courseId){
        initOutDto.getPayload().setLsCourseId(courseId);
        initOutDto.getPayload().setStatus(CourseKafkaStatus.IMPORTED);
        kafkaTemplate.send("lms.course.status", key, initOutDto);
    }

    public void sendOutMessageWithValidationErrors(String key, CourseOutDto initOutDto, List<String> validationErrors){
        initOutDto.getPayload().setErrors(validationErrors);
        initOutDto.getPayload().setStatus(CourseKafkaStatus.DENIED);
        kafkaTemplate.send("lms.course.status.dlq", key, initOutDto);
    }

    public void sendOutMessageWithException(String key, CourseOutDto initOutDto, String exceptionMessage){
        initOutDto.getPayload().setErrors(List.of(exceptionMessage));
        initOutDto.getPayload().setStatus(CourseKafkaStatus.DENIED);
        kafkaTemplate.send("lms.course.status.dlq", key, initOutDto);
    }

    public List<String> getValidationErrors(CourseDto courseDto){
        BindingResult bindingResult = new BeanPropertyBindingResult(courseDto, "courseDto");
        validator.validate(courseDto, bindingResult);
        return bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

    public CourseDto initCourseDto(CourseInDto inDto){
        CourseDto courseDto = courseMapper.toDto(inDto.getPayload());
        User author = userService.findByEmail(inDto.getPayload().getAuthorEmail());
        courseDto.setAuthorId(author.getId());
        courseDto.setStatus(CourseStatus.PUBLISHED);
        return courseDto;
    }

    public CourseOutDto initCourseOutDto(CourseInDto inDto){
        CourseOutDto outDto = new CourseOutDto();
        outDto.setEventId(eventId.getAndIncrement());
        outDto.setSystemId(systemId);
        outDto.setTimestamp(Instant.now());
        outDto.setPayload(new PayloadOutDto());
        outDto.getPayload().setExternalCourseId(inDto.getPayload().getExternalCourseId());
        outDto.getPayload().setAuthorEmail(inDto.getPayload().getAuthorEmail());
        return  outDto;
    }
}
