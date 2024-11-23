package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.ClassSubjectDto;
import com.technovate.school_management.dto.CreateClassSubjectDto;
import com.technovate.school_management.entity.enums.ClassEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClassSubjectService {
    void addSubjectToClass(CreateClassSubjectDto createClassSubjectDto);
    Page<ClassSubjectDto> findAllClassSubjects(int page, int size);
    List<ClassSubjectDto> findBySchoolClassName(String schoolClassName);
}
