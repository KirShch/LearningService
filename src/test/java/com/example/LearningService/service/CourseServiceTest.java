package com.example.LearningService.service;

import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.CourseMapper;
import com.example.LearningService.repository.CourseRepository;
import com.example.LearningService.exception.UserIncorrectRoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private UserService userService;

    private User user;
    private CourseService courseService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        courseService = new CourseService(courseRepository, courseMapper, userService);

        user = new User();
        user.setId(1L);
        user.setEmail("email@test.t");
        user.setPassword("1234");
        user.setFirstName("A");
        user.setLastName("B");
        user.setRole(Role.ADMIN);
    }

    @Test
    void buildCourse() {
        CourseDto courseDto = new CourseDto("Addition", "adding", 1L, CourseStatus.PUBLISHED);
        Course course = new Course();
        course.setTitle("Addition");
        course.setDescription("adding");
        course.setId(1L);
        course.setStatus(CourseStatus.PUBLISHED);

        when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(course);
        when(userService.findById(1L)).thenReturn(user);

        Course result = courseService.buildCourse(courseDto);

        assertNotNull(result.getId());
        assertEquals("Addition", result.getTitle());
        assertEquals("adding", result.getDescription());
        assertEquals(user, result.getAuthor());
        assertEquals(CourseStatus.PUBLISHED, result.getStatus());
    }

    @Test
    void verifyAuthor() {
        user.setRole(Role.ADMIN);
        assertDoesNotThrow(() -> courseService.verifyAuthor(user));
        user.setRole(Role.TEACHER);
        assertDoesNotThrow(() -> courseService.verifyAuthor(user));
        user.setRole(Role.STUDENT);
        assertThrows(UserIncorrectRoleException.class, () -> courseService.verifyAuthor(user));
    }

    @Test
    void updateCourse() {
        CourseUpdateDto courseUpdateDto = new CourseUpdateDto("division", "divide", CourseStatus.ARCHIVED);

        Course course = new Course();
        course.setTitle("Addition");
        course.setDescription("ädding");
        course.setId(1L);
        course.setStatus(CourseStatus.PUBLISHED);
        course.setAuthor(user);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.updateCourse(courseUpdateDto, 1L);

        assertNotNull(result.getId());
        assertEquals("division", result.getTitle());
        assertEquals("divide", result.getDescription());
        assertEquals(CourseStatus.ARCHIVED, result.getStatus());
        verify(courseRepository, times(1)).save(course);
    }
}