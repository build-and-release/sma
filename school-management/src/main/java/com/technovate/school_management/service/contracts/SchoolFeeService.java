package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateSchoolFeeDto;
import com.technovate.school_management.dto.SchoolFeeListDto;

public interface SchoolFeeService {
    void CreateSchoolFee(CreateSchoolFeeDto createSchoolFeeDto);
    SchoolFeeListDto findSchoolClassFeeForCurrentTerm(long schoolClasId);
    void addFeeToAllStudentsInClassForTerm(long schoolFeeId, long classId);
}
