package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateGradeDto;
import com.technovate.school_management.dto.GradeListDto;

import java.util.List;

public interface GradeService {
    void createGrade(CreateGradeDto createGradeDto);
    List<GradeListDto> findGradesByResultId(Long resultId);
    void createGradeForStudent(String studentIdNumber, Long classId);
}
