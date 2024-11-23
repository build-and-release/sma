package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.GradeDto;
import com.technovate.school_management.dto.GradeListDto;
import com.technovate.school_management.entity.Grade;
import com.technovate.school_management.entity.enums.GradeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GradeMapper {
    GradeDto toGradeDto(Grade grade);
    List<GradeDto> toGradeDtos(List<Grade> grade);
    @Mapping(source = "result.id", target = "resultId")
    @Mapping(source = "result.student.idNumber", target = "studentIdNumber")
    @Mapping(source = "result.term.name", target = "term")
    @Mapping(source = "classSubject.schoolClass.name", target = "schoolClass")
    @Mapping(source = "classSubject.subject.title", target = "subject")
    GradeListDto toGradeListDto(Grade grade);
    default List<GradeListDto> toGradeListDtos(List<Grade> grades) {
        if (grades == null) {
            return null;
        }
        return grades.stream().map(this::toGradeListDto).toList();
    }
    default String gradeOptionToString(GradeOption gradeOption) {
        if (gradeOption == null) {
            return null;
        }
        return gradeOption.name();
    }
}
