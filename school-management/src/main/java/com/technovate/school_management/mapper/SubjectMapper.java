package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.CreateSubjectDto;
import com.technovate.school_management.dto.SubjectDto;
import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {
    Subject toSubject(CreateSubjectDto createSubjectDto);
    List<SubjectDto> toSubjectDtos(List<Subject> subject);
    SubjectDto toSubjectDto(Subject subject);
    default Page<SubjectDto> toSubjectDtos(Page<Subject> subjects) {
        return subjects.map(this::toSubjectDto);
    }

    default String toSubjectString(ClassLevelEnum classLevelEnum) {
        if (classLevelEnum == null) {
            return null;
        }
        String lower = classLevelEnum.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    default ClassLevelEnum toClassLevelEnum(String classLevel) {
        if (classLevel == null) {
            return null;
        }

        return ClassLevelEnum.valueOf(classLevel.toUpperCase());
    }
}
