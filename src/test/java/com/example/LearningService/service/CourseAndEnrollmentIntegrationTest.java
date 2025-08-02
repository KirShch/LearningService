package com.example.LearningService.service;

import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.dto.EnrollmentDto;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.repository.CourseRepository;
import com.example.LearningService.repository.EnrollmentRepository;
import com.example.LearningService.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@EnableCaching
public class CourseAndEnrollmentIntegrationTest {
    /**
     *
     */

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CacheManager cacheManager;

    private static List<UserDto> userDtoList;
    private static List<CourseDto> courseDtoList;
    private static List<EnrollmentDto> enrollmentDtoList;

    @BeforeAll
    static void initDto(){
        userDtoList = List.of(
                new UserDto("test1@t.t", "1234", "FN 1", "LN 1", Role.ADMIN),
                new UserDto("test2@t.t", "1234", "FN 2", "LN 2", Role.TEACHER),
                new UserDto("test3@t.t", "1234", "FN 3", "LN 3", Role.STUDENT),
                new UserDto("test4@t.t", "1234", "FN 4", "LN 4", Role.STUDENT)
        );
        courseDtoList = List.of(
                new CourseDto("Add", "Add", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Subtract", "Subtract", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Multiply", "Multiply", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Divide", "Divide", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Root", "Root", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Power", "Power", 2L, CourseStatus.PUBLISHED),
                new CourseDto("Log", "Log", 2L, CourseStatus.PUBLISHED)
        );
        enrollmentDtoList = List.of(
                new EnrollmentDto(1L, 1L),
                new EnrollmentDto(1L, 2L),
                new EnrollmentDto(1L, 3L),
                new EnrollmentDto(1L, 4L),
                new EnrollmentDto(1L, 5L),
                new EnrollmentDto(1L, 6L),
                new EnrollmentDto(1L, 7L),
                new EnrollmentDto(2L, 2L),
                new EnrollmentDto(2L, 4L),
                new EnrollmentDto(2L, 6L),
                new EnrollmentDto(3L, 4L),
                new EnrollmentDto(3L, 6L),
                new EnrollmentDto(4L, 4L)
        );
        // courses by enrollments desc (4 - 4ner, 6 - 3enr, 2 - 2enr, an2 other 2)
    }

    @Test
    void testContainerIsRunning() {
        assertTrue(postgres.isRunning());
        assertEquals(postgres.getDatabaseName(),"testdb");
    }

    @Test
    void shouldSaveUserToRealDatabase() {
        User user = userService.userRegistration(userDtoList.get(0));
        assertNotNull(user.getId());
        assertEquals(user.getId(), 1L);
        //userRepository.deleteById(6L);
    }

    @Test
    void shouldSaveAllEntity(){
        //user 0 always saved
        for (int i = 1; i < userDtoList.size(); i++){
            userService.userRegistration(userDtoList.get(i));
        }
        for (int i = 0; i < courseDtoList.size(); i++){
            courseService.createCourse(courseDtoList.get(i));
        }
        for (int i = 1; i < enrollmentDtoList.size(); i++){
            enrollmentService.createEnrollment(enrollmentDtoList.get(i));
        }

        for(int i = 0; i < userDtoList.size(); i++){
            assertEquals(userService.findById((long)(i+1)).getEmail(), userDtoList.get(i).getEmail());
        }
        for(int i = 0; i < courseDtoList.size(); i++){
            assertEquals(courseService.findById((long)(i+1)).getTitle(), courseDtoList.get(i).getTitle());
        }
        for(int i = 0; i < enrollmentDtoList.size(); i++){
            assertEquals(enrollmentRepository.findById((long)(i+1)).get().getCourse().getId(), enrollmentDtoList.get(i).courseId());
        }
    }

    @Test
    void courseDetailTest(){
        Course course = courseService.findById(1L);
        assertEquals(course.getAuthor(), userService.findById(2L));
    }

    @Test
    void cacheUsingTaste(){
        long startTime = System.currentTimeMillis();
        List<Course> firstCall = enrollmentService.getTop5CoursesByEnrollments();
        long firstCallDuration = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        List<Course> secondCall = enrollmentService.getTop5CoursesByEnrollments();
        long secondCallDuration = System.currentTimeMillis() - startTime;

        assertTrue(secondCallDuration < firstCallDuration);
        System.out.println("FIRST     =      " + firstCallDuration);
        System.out.println("SECOND     =      " + secondCallDuration);

        assertEquals(firstCall, secondCall);

        Cache top5Cache = cacheManager.getCache("top5CoursesByEnrollments");
        assertNotNull(top5Cache);
    }

    @Test
    void top5CoursesByEnrollments(){
        List<Course> top = enrollmentService.getTop5CoursesByEnrollments();
        Set<Long> topId = top.stream()
                .map(c -> c.getAuthor().getId())
                .collect(Collectors.toSet());
        assertThat(topId.contains(4L));
        assertThat(topId.contains(6L));
        assertThat(topId.contains(2L));
        assertThat(topId.size()==5);
    }
}
