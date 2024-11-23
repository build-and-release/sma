package com.technovate.school_management.service;

import com.technovate.school_management.dto.ClassSubjectDto;
import com.technovate.school_management.dto.CreateClassSubjectDto;
import com.technovate.school_management.entity.ClassSubject;
import com.technovate.school_management.entity.SchoolClass;
import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.entity.enums.ClassEnum;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.ClassSubjectMapper;
import com.technovate.school_management.repository.ClassSubjectRepository;
import com.technovate.school_management.repository.SchoolClassRepository;
import com.technovate.school_management.repository.SubjectRepository;
import com.technovate.school_management.service.contracts.ClassSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassSubjectServiceImpl implements ClassSubjectService  {
    private final ClassSubjectMapper classSubjectMapper;
    private final ClassSubjectRepository classSubjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public void addSubjectToClass(CreateClassSubjectDto createClassSubjectDto) {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(createClassSubjectDto.getSchoolClassId());
        if (schoolClass.isEmpty()) {
            throw new ResourceNotFoundException("No school class with the id" + createClassSubjectDto.getSchoolClassId());
        }

        Optional<Subject> subject = subjectRepository.findById(createClassSubjectDto.getSubjectId());
        if (subject.isEmpty()) {
            throw new ResourceNotFoundException("No subject class with the id" + createClassSubjectDto.getSubjectId());
        }

        ClassSubject classSubject = classSubjectMapper.toClassSubject(createClassSubjectDto);
        classSubject.setSchoolClass(schoolClass.get());
        classSubject.setSubject(subject.get());
        if (createClassSubjectDto.getSubjectCode() == null) {
            classSubject.setSubjectCode(subject.get().getSubjectCode());
        }
        classSubjectRepository.save(classSubject);
    }

    @Override
    public Page<ClassSubjectDto> findAllClassSubjects(int page, int size) {
        Page<ClassSubject> classSubjects = classSubjectRepository.findAll(PageRequest.of(page, size));
        return classSubjectMapper.toClassSubjectDtos(classSubjects);
    }

    @Override
    public List<ClassSubjectDto> findBySchoolClassName(String schoolClassName) {
        boolean isValidClassName = isValidClassName(schoolClassName);
        if (!isValidClassName) {
            throw new ResourceNotFoundException("The provided class name: " + schoolClassName.toUpperCase() +
                    " is not a valid class name");
        }
        List<ClassSubject> classSubjects = classSubjectRepository.findBySchoolClassName(ClassEnum.valueOf(schoolClassName.toUpperCase()));
        return classSubjectMapper.toClassSubjectDtos(classSubjects);
    }

    private boolean isValidClassName(String className) {
        try {
            ClassEnum.valueOf(className.toUpperCase());
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
