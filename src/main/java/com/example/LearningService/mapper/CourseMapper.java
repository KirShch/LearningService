package com.example.LearningService.mapper;

import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);
}
