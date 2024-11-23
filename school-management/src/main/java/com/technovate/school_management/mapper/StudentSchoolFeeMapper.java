package com.technovate.school_management.mapper;

import com.technovate.school_management.dto.StudentSchoolFeeDto;
import com.technovate.school_management.dto.StudentSchoolFeeDtoPaystack;
import com.technovate.school_management.dto.StudentSchoolFeePaymentDto;
import com.technovate.school_management.entity.Student;
import com.technovate.school_management.entity.StudentSchoolFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SchoolClassMapper.class})
public interface StudentSchoolFeeMapper {
    @Mapping(source = "studentSchoolFee.schoolFee.term.name", target = "term")
    @Mapping(source = "studentSchoolFee.student.idNumber", target = "studentIdNumber")
    @Mapping(source = "studentSchoolFee.schoolFee.fee.dueDate", target = "dueDate")
    @Mapping(source = "studentSchoolFee.schoolFee.schoolClass", target = "schoolClass")
    @Mapping(source = "studentSchoolFee.schoolFee.fee.description", target = "description")
    StudentSchoolFeeDto toStudentSchoolFeeDto(StudentSchoolFee studentSchoolFee);
    default Page<StudentSchoolFeeDto> toStudentSchoolFeeDtos(Page<StudentSchoolFee> studentSchoolFees) {
        return studentSchoolFees.map(this::toStudentSchoolFeeDto);
    }
    default List<StudentSchoolFeeDto> toStudentSchoolFeeDtos(List<StudentSchoolFee> studentSchoolFees) {
        return studentSchoolFees.stream().map(this::toStudentSchoolFeeDto).toList();
    }
    @Mapping(source = "studentSchoolFee.id", target = "feeId")
    @Mapping(source = "studentSchoolFee.student.idNumber", target = "studentIdNumber")
    StudentSchoolFeeDtoPaystack toStudentSchoolFeeDtoPaystack(StudentSchoolFee studentSchoolFee);
}
