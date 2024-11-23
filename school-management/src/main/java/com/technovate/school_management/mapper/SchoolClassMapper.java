package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.SchoolClassDto;
import com.technovate.school_management.entity.SchoolClass;
import com.technovate.school_management.entity.enums.ClassEnum;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SchoolClassMapper {
    @Mapping(source = "level", target = "level")
    @Mapping(source = "name", target = "name")
    SchoolClassDto toSchoolClassDto(SchoolClass schoolClass);

    default String mapClassLevelToString(ClassLevelEnum classLevelEnum) {
        if (classLevelEnum == null) {
            return null;
        }
        String lower = classLevelEnum.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0))
                + lower.substring(1);
    }

    default String mapClassEnumToString(ClassEnum classEnum) {
        if (classEnum == null) {
            return null;
        }
        String lower = classEnum.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0))
                + lower.substring(1);
    }
}
