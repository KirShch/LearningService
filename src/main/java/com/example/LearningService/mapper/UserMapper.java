package com.example.LearningService.mapper;

import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(User user);
/*
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)*/
    //@BeanMapping(ignoreByDefault = true)
    User toEntity(UserDto userDto);

    /*// Можно добавлять специальные маппинги
    @Mapping(target = "username", source = "login") // если имена полей разные
    User toEntityWithDifferentFields(UserDto userDto);*/
}
