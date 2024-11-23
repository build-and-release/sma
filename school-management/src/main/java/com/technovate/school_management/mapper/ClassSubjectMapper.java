package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.ClassSubjectDto;
import com.technovate.school_management.dto.CreateClassSubjectDto;
import com.technovate.school_management.dto.SubjectDto;
import com.technovate.school_management.entity.ClassSubject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassSubjectMapper {
    ClassSubject toClassSubject(CreateClassSubjectDto createClassSubjectDto);
    ClassSubjectDto toClassSubjectDto(ClassSubject classSubject);

    default Page<ClassSubjectDto> toClassSubjectDtos(Page<ClassSubject> classSubjects) {
        return classSubjects.map(this::toClassSubjectDto);
    }

    List<ClassSubjectDto> toClassSubjectDtos(List<ClassSubject> classSubjects);
}
