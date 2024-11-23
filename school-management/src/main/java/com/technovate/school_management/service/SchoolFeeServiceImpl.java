package com.technovate.school_management.service;

import com.technovate.school_management.dto.CreateSchoolFeeDto;
import com.technovate.school_management.dto.SchoolFeeListDto;
import com.technovate.school_management.entity.*;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.SchoolFeeMapper;
import com.technovate.school_management.repository.*;
import com.technovate.school_management.service.contracts.SchoolFeeService;
import com.technovate.school_management.service.contracts.StudentSchoolFeeService;
import com.technovate.school_management.service.contracts.StudentService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolFeeServiceImpl implements SchoolFeeService {
    private final SchoolFeeRepository schoolFeeRepository;
    private final TermRepository termRepository;
    private final FeeRepository feeRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolFeeMapper schoolFeeMapper;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentSchoolFeeService studentSchoolFeeService;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Override
    public void CreateSchoolFee(CreateSchoolFeeDto createSchoolFeeDto) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(createSchoolFeeDto);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append(";"));
            throw new BadRequestException(stringBuilder.toString());
        }

//        Get Fee
        Optional<Fee> feeOpt = feeRepository.findById(createSchoolFeeDto.getFeeId());
        if (feeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Fee with the id " + createSchoolFeeDto.getFeeId() + " not found");
        }
//        Get Class
        Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findById(createSchoolFeeDto.getSchoolClassId());
        if (schoolClassOpt.isEmpty()) {
            throw new ResourceNotFoundException("Class with the id " + createSchoolFeeDto.getSchoolClassId() + " not found");
        }

//        Get current term
        Optional<Term> termOpt = termRepository.findByIsCurrentTrue();

        if (termOpt.isEmpty()) {
            throw new ResourceNotFoundException("No current term set");
        }
// Verify is there isn't already a fee for that class for the term.
        Optional<SchoolFee> schoolFeeExistOpt = schoolFeeRepository.findBySchoolClassIdAndTermId(createSchoolFeeDto.getSchoolClassId(), termOpt.get().getId());
        if (schoolFeeExistOpt.isPresent()) {
            throw new BadRequestException("A school fee has already been created for the class");
        }
        SchoolFee schoolFee = new SchoolFee();
        schoolFee.setFee(feeOpt.get());
        schoolFee.setTerm(termOpt.get());
        schoolFee.setSchoolClass(schoolClassOpt.get());
        schoolFeeRepository.save(schoolFee);
        addFeeToAllStudentsInClassForTerm(schoolFee.getId(), createSchoolFeeDto.getSchoolClassId())
        ;
    }

    @Override
    public SchoolFeeListDto findSchoolClassFeeForCurrentTerm(long schoolClasId) {
        //        Get Class
        Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findById(schoolClasId);
        if (schoolClassOpt.isEmpty()) {
            throw new ResourceNotFoundException("Class with the id " + schoolClasId + " not found");
        }

//        Get current term
        Optional<Term> termOpt = termRepository.findByIsCurrentTrue();

        if (termOpt.isEmpty()) {
            throw new ResourceNotFoundException("No current term set");
        }

//        Find fee
        Optional<SchoolFee> schoolFeeOpt = schoolFeeRepository.findBySchoolClassIdAndTermId(schoolClasId, termOpt.get().getId());
        if(schoolFeeOpt.isEmpty()) {
            throw new ResourceNotFoundException("No school fee for current class yet");
        }
        return schoolFeeMapper.toSchoolFeeListDto(schoolFeeOpt.get());
    }

    @Override
    @Async
    public void addFeeToAllStudentsInClassForTerm(long schoolFeeId, long classId) {
        Optional<Term> termOpt = termRepository.findByIsCurrentTrue();

        if (termOpt.isEmpty()) {
            logger.info("No current term set");
            return;
        }
        Optional<SchoolFee> schoolFeeOpt = schoolFeeRepository.findBySchoolClassIdAndTermId(classId, termOpt.get().getId());
        if (schoolFeeOpt.isEmpty()) {
            logger.info("No fee available for current class");
            return;
        }
        List<StudentClass> studentClasses = studentClassRepository.findAllBySchoolClassId(classId);
        if (studentClasses.isEmpty()) {
            logger.info("No students found for class with the id: {}", classId);
        }
        for (StudentClass studentClass : studentClasses) {
            studentSchoolFeeService.addSchoolFeeForStudent(studentClass.getStudent().getId(), classId);
        }
    }
}
