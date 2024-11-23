package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateUserDto;
import com.technovate.school_management.dto.UserDto;
import com.technovate.school_management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User createUserDtoToUser(CreateUserDto createUserDto);
    List<UserDto> usersToUserDtos(List<User> users);
}
