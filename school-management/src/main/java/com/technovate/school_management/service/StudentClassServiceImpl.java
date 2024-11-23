package com.technovate.school_management.service;

import com.technovate.school_management.dto.StudentClassDto;
import com.technovate.school_management.entity.Student;
import com.technovate.school_management.entity.StudentClass;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.StudentClassMapper;
import com.technovate.school_management.repository.StudentClassRepository;
import com.technovate.school_management.service.contracts.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentClassServiceImpl implements StudentClassService  {
    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;

    @Override
    public StudentClassDto findStudentByIdWithCurrentClass(Long studentId) {
        Optional<StudentClass> studentClassOpt = studentClassRepository.findFirstByStudentIdAndIsCurrent(studentId, true);
        if (studentClassOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student with the id " + studentId + " not found");
        }
        StudentClass studentClass = studentClassOpt.get();
        return studentClassMapper.toStudentClassDto(studentClass);
    }

    @Override
    public Page<StudentClassDto> findStudentsWithCurrentClass(int page, int size) {
        Page<StudentClass> studentClasses = studentClassRepository.findAllByIsCurrent(true, PageRequest.of(page, size));
        return studentClassMapper.toStudentClassDtos(studentClasses);
    }
}
