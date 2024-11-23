package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.StudentClassDto;
import org.springframework.data.domain.Page;

public interface StudentClassService {
    StudentClassDto findStudentByIdWithCurrentClass(Long studentId);
    Page<StudentClassDto> findStudentsWithCurrentClass(int page, int size);
}
