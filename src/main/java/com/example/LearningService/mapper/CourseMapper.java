package com.example.LearningService.mapper;

import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    CourseDto toDto(Course course);

    /*@Mapping(target = "author", source = "user")
    Course toEntity(CourseDto courseDto, User user);*/
    Course toEntity(CourseDto courseDto);
}
