package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.ResultListDto;
import com.technovate.school_management.entity.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResultMapper {
    @Mapping(source = "result.id", target = "id")
    @Mapping(source = "result.studentClass.student.idNumber", target = "studentIdNumber") // Mapping idNumber from Student
    @Mapping(source = "result.studentClass.schoolClass.name", target = "schoolClass") // Mapping name from SchoolClass
    @Mapping(source = "result.term.name", target = "term") // Mapping name from Term (SchoolTerm Enum)
    ResultListDto toResultListDto(Result result);

    default List<ResultListDto> toResultListDtos(List<Result> results) {
        if (results == null) {
            return null;
        }
        return results.stream().map(this::toResultListDto).toList();
    }
}
