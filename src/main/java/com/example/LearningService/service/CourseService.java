package com.example.LearningService.service;

import com.example.LearningService.LearningServiceBuisnessException.UserIncorrectRoleException;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.CourseMapper;
import com.example.LearningService.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;

    @Transactional
    public Course createCourse(CourseDto courseDto){
        User user = userService.findById(courseDto.getAuthorId());

        if (user.getRole() != Role.ADMIN && user.getRole() != Role.TEACHER)
            throw new UserIncorrectRoleException("User role is incorrect");

        System.out.println("____\n____\n____\n____\n____\n____\n____\n____\n____\n");
        System.out.println(courseDto);

        Course course = courseMapper.toEntity(courseDto);
        course.setCreatedAt(Instant.now());
        course.setAuthor(user);

        System.out.println(course);

        return courseRepository.save(course);
    }

    public Optional<Course> findById(Long id){
        return courseRepository.findById(id);
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }
}
