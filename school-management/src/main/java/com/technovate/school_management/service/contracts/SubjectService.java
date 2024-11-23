package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateSubjectDto;
import com.technovate.school_management.dto.SubjectDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectService {
    void addSubject(CreateSubjectDto createSubjectDto);
    Page<SubjectDto> findSubjects(int page, int size);
    List<SubjectDto> findAllByLevel(String level);
}
