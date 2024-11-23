package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.StudentClassDto;
import com.technovate.school_management.entity.StudentClass;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentClassMapper {
    StudentClassDto toStudentClassDto(StudentClass studentClass);
    default Page<StudentClassDto> toStudentClassDtos(Page<StudentClass> studentClasses) {
        return studentClasses.map(this::toStudentClassDto);
    }
}
