package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.StudentDto;
import com.technovate.school_management.dto.CreateStudentDto;
import com.technovate.school_management.entity.Student;
import com.technovate.school_management.entity.enums.GenderEnum;
import com.technovate.school_management.entity.enums.GuardianType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    @Mapping(source = "gender", target = "gender")
    Student toStudent(CreateStudentDto createStudentDto);
    @Mapping(source = "gender", target = "gender")
    StudentDto toStudentDto(Student student);
    List<StudentDto> toStudentDtos(List<Student> students);
    default Page<StudentDto> toPageStudentDto(Page<Student> students) {
        return students.map(this::toStudentDto);
    }
    // Custom method to map the lowercase string to the enum
    default GenderEnum mapStringToGender(String status) {
        if (status == null) {
            return null;
        }
        return GenderEnum.valueOf(status.toUpperCase()); // Convert string to uppercase
    }

    default String mapGenderToString(GenderEnum status) {
        if (status == null) {
            return null;
        }
        String lower = status.name().toLowerCase();  // Convert enum name to lowercase
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);  // Capitalize first letter // Convert string to uppercase
    }

    default String guardianTypeToString(GuardianType guardianType) {
        if (guardianType == null) {
            return null;
        }
        String lower = guardianType.name().toLowerCase();  // Convert enum name to lowercase
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    default GuardianType stringToGuardianType(String guardianType) {
        if (guardianType == null) {
            return null;
        }
        return GuardianType.valueOf(guardianType.toUpperCase()); // Convert string to uppercase
    }
}
