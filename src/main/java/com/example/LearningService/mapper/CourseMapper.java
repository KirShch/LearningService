package com.example.LearningService.mapper;

import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.dto.PayloadInDto;
import com.example.LearningService.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    CourseDto toDto(Course course);

    CourseDto toDto(PayloadInDto payloadInDto);

    /*@Mapping(target = "author", source = "user")
    Course toEntity(CourseDto courseDto, User user);*/
    Course toEntity(CourseDto courseDto);
}
