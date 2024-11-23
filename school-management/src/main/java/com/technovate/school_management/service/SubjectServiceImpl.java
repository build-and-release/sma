package com.technovate.school_management.service;

import com.technovate.school_management.dto.CreateSubjectDto;
import com.technovate.school_management.dto.SubjectDto;
import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import com.technovate.school_management.mapper.SubjectMapper;
import com.technovate.school_management.repository.SubjectRepository;
import com.technovate.school_management.service.contracts.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    @Override
    public void addSubject(CreateSubjectDto createSubjectDto) {
        Subject subject = subjectMapper.toSubject(createSubjectDto);
        subjectRepository.save(subject);
    }

    @Override
    public Page<SubjectDto> findSubjects(int page, int size) {
        Page<Subject> subjects = subjectRepository.findAll(PageRequest.of(page, size));
        return subjectMapper.toSubjectDtos(subjects);
    }

    @Override
    public List<SubjectDto> findAllByLevel(String level) {
        if (!isValidClassLevelEnum(level)) {
            throw new IllegalArgumentException(level + " is not a valid class level");
        }
        ClassLevelEnum parsedLevel = ClassLevelEnum.valueOf(level.toUpperCase());
        List<Subject> subjects = subjectRepository.findAllByLevel(parsedLevel);
        return subjectMapper.toSubjectDtos(subjects);
    }

    private boolean isValidClassLevelEnum(String level) {
        try {
            ClassLevelEnum.valueOf(level.toUpperCase()); // Attempt to parse the string
            return true; // Parsing successful
        } catch (IllegalArgumentException e) {
            return false; // Parsing failed, return false
        }
    }
}
