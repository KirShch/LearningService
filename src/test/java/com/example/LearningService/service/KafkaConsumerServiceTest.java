package com.example.LearningService.service;

import com.example.LearningService.config.KafkaProperties;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.dto.CourseInDto;
import com.example.LearningService.dto.CourseOutDto;
import com.example.LearningService.dto.PayloadInDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.exception.UserEmailExistsException;
import com.example.LearningService.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class KafkaConsumerServiceTest {
    @Mock
    private KafkaProducerService kafkaProducerService;
    @Mock
    private UserService userService;
    @Mock
    private CourseService courseService;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private Validator validator;
    @Mock
    private KafkaProperties kafkaProperties;

    private KafkaConsumerService kafkaConsumerService;
    private User user;
    private Course course;
    private CourseInDto courseInDto;
    private CourseDto courseDto;
    private String key;

    @BeforeEach
    void initDto(){
        MockitoAnnotations.openMocks(this);
        kafkaConsumerService = new KafkaConsumerService(kafkaProducerService, userService, courseService, courseMapper, validator, kafkaProperties);
        key = "key";

        user = new User();
        user.setId(1L);
        user.setEmail("email@test.t");
        user.setPassword("1234");
        user.setFirstName("A");
        user.setLastName("B");
        user.setRole(Role.ADMIN);

        course = new Course();
        course.setId(1L);
        course.setTitle("Addition");
        course.setDescription("Adding 2 numbers");
        course.setAuthor(user);
        course.setStatus(CourseStatus.PUBLISHED);

        courseInDto = new CourseInDto();
        courseInDto.setEventId(1L);
        courseInDto.setSystemId(kafkaProperties.getSystemId());
        courseInDto.setPayload(new PayloadInDto());
        courseInDto.getPayload().setTitle(course.getTitle());
        courseInDto.getPayload().setDescription(course.getDescription());
        courseInDto.getPayload().setExternalCourseId(11L);
        courseInDto.getPayload().setAuthorEmail(course.getAuthor().getEmail());

        courseDto = new CourseDto();
        courseDto.setTitle(courseInDto.getPayload().getTitle());
        courseDto.setDescription(courseInDto.getPayload().getDescription());
    }

    @Test
    void processCourseCreation_sendOutMessage() {
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(courseMapper.toDto(courseInDto.getPayload())).thenReturn(courseDto);
        when(courseService.createCourse(any(CourseDto.class))).thenReturn(course);

        kafkaConsumerService.processCourseCreation(key, courseInDto);

        verify(kafkaProducerService, times(1))
                .sendOutMessage(anyString(), any(CourseOutDto.class), anyLong());
        /*verify(kafkaProducerService, times(1))
                .sendOutMessageWithException(anyString(), any(CourseOutDto.class), anyString());
        verify(kafkaProducerService, times(1))
                .sendOutMessageWithValidationErrors(anyString(), any(CourseOutDto.class), anyList());*/
    }

    @Test
    void processCourseCreation_sendOutMessageWithException() {
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(courseMapper.toDto(courseInDto.getPayload())).thenReturn(courseDto);
        when(courseService.createCourse(any(CourseDto.class))).thenThrow(new UserEmailExistsException("email does not exist"));

        kafkaConsumerService.processCourseCreation(key, courseInDto);

        verify(kafkaProducerService, times(1))
                .sendOutMessageWithException(anyString(), any(CourseOutDto.class), anyString());
    }

    @Test
    void processCourseCreation_sendOutMessageWithValidationErrors() {
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(courseMapper.toDto(courseInDto.getPayload())).thenReturn(courseDto);
        when(courseService.createCourse(any(CourseDto.class))).thenReturn(course);

        doAnswer(invocation -> {
            BindingResult bindingResult = invocation.getArgument(1);
            bindingResult.rejectValue("title", "incorrect value");
            return bindingResult;
        }).when(validator).validate(any(CourseDto.class), any(BindingResult.class));

        kafkaConsumerService.processCourseCreation(key, courseInDto);

        verify(kafkaProducerService, times(1))
                .sendOutMessageWithValidationErrors(anyString(), any(CourseOutDto.class), anyList());
    }
}