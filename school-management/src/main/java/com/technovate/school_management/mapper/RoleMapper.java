package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.RoleDto;
import com.technovate.school_management.entity.Role;
import com.technovate.school_management.entity.enums.UserRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    @Mapping(source = "role", target = "role")
    RoleDto toRoleDto(Role role);
    List<RoleDto> toRoleDtos(List<Role> roles);
    default String roleEnumToString(UserRoles userRoles) {
        if (userRoles == null) {
            return null;
        }
        return userRoles.name();
    }
}
