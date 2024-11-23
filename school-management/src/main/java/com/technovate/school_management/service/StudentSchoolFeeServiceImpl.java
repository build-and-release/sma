package com.technovate.school_management.service;

import com.technovate.school_management.dto.*;
import com.technovate.school_management.entity.*;
import com.technovate.school_management.entity.enums.PaymentStatus;
import com.technovate.school_management.exception.BadRequestException;
import com.technovate.school_management.exception.ResourceNotFoundException;
import com.technovate.school_management.mapper.StudentSchoolFeeMapper;
import com.technovate.school_management.model.PaystackPaymentData;
import com.technovate.school_management.model.PaystackPaymentWebHookRequest;
import com.technovate.school_management.model.StudentSchoolFeePaymentRequest;
import com.technovate.school_management.model.enums.PAYMENT_TYPE;
import com.technovate.school_management.repository.*;
import com.technovate.school_management.service.contracts.PaymentService;
import com.technovate.school_management.service.contracts.StudentSchoolFeeService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentSchoolFeeServiceImpl implements StudentSchoolFeeService {
    private final SchoolFeeRepository schoolFeeRepository;
    private final StudentRepository studentRepository;
    private final StudentSchoolFeeRepository studentSchoolFeeRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentSchoolFeeMapper studentSchoolFeeMapper;
    private final PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    @Async
    public void addSchoolFeeForStudent(Long studentId, Long schoolClassId) {
//        Find student
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as no student was found with the id", studentId, schoolClassId);
            return;
        }
//        Find school class
        Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findById(schoolClassId);
        if (schoolClassOpt.isEmpty()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as no school class was found with the id", studentId, schoolClassId);
            return;
        }
//        Confirm if the current class of the student is the class for which the school fee is being set.
//        a. Find the student's current class.
        Optional<StudentClass> studentClassOpt = studentClassRepository.findFirstByStudentIdAndIsCurrent(studentId, true);
        if (studentClassOpt.isEmpty()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as student's current class could not be resolved.", studentId, schoolClassId);
            return;
        }
//        b. Compare the current class to the class Id passed.
        if (!studentClassOpt.get().getSchoolClass().getId().equals(schoolClassId)) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as student's current class with the id {} is not the same as the school class Id request.", studentId, schoolClassId, studentClassOpt.get().getSchoolClass().getId());
            return;
        }
//        Find the current term
        Optional<Term> termOpt = termRepository.findByIsCurrentTrue();
        if (termOpt.isEmpty()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as current term was not set up", studentId, schoolClassId);
            return;
        }
//        Find the current school fee for the class
        Optional<SchoolFee> schoolFeeOpt = schoolFeeRepository.findBySchoolClassIdAndTermId(schoolClassId, termOpt.get().getId());
        if (schoolFeeOpt.isEmpty()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as no school fee has been create for the class", studentId, schoolClassId);
            return;
        }
//        Check if the student already has school fee record for this class and term.
        Optional<StudentSchoolFee> studentSchoolFeeExistOpt = studentSchoolFeeRepository.findByStudentIdAndSchoolFeeTermId(studentId, termOpt.get().getId());
        if (studentSchoolFeeExistOpt.isPresent()) {
            logger.error("Attempt to add school fee for student with id {} and for school class with the id {} failed as the student already has a fee for this class", studentId, schoolClassId);
            return;
        }

//        At this point there is a school fee, student and school class (and that the class is the student's current class) therefore we proceed to create the fee.
        Student student = studentOpt.get();
        SchoolFee schoolFee = schoolFeeOpt.get();
        StudentSchoolFee studentSchoolFee = new StudentSchoolFee();
        studentSchoolFee.setStudent(student);
        studentSchoolFee.setSchoolFee(schoolFee);
//        if the due date of the school fee has pass, set the due fee to the total fee plus late fee, else set the due fee to the total fee.
        Fee fee = schoolFee.getFee();
        if (fee.getDueDate().isAfter(LocalDate.now())) {
            studentSchoolFee.setTotalDueAmount(fee.getTotal() + fee.getLateFee());
        } else {
            studentSchoolFee.setTotalDueAmount(fee.getTotal());
        }
        studentSchoolFeeRepository.save(studentSchoolFee);
        logger.info("Successfully added school fee for school with id {} for class with id {} and {} term", studentId, schoolClassId, termOpt.get().getName());
    }

    @Override
    public Page<StudentSchoolFeeDto> findAllByStudentIdNumber(FindByStudentIdNumberPaginatedDto findByStudentIdNumberPaginatedDto) {
        Validator validator;
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(findByStudentIdNumberPaginatedDto);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }
        Page<StudentSchoolFee> studentSchoolFeePage = studentSchoolFeeRepository.findAllByStudentIdNumber(
          findByStudentIdNumberPaginatedDto.getIdNumber(),
                PageRequest.of(findByStudentIdNumberPaginatedDto.getPage(), findByStudentIdNumberPaginatedDto.getSize())
        );
        return studentSchoolFeeMapper.toStudentSchoolFeeDtos(studentSchoolFeePage);
    }

    @Override
    public List<StudentSchoolFeeDto> findAllByStudentIdNumberAndPaymentStatus(String studentIdNumber, String paymentStatus) {
        return List.of();
    }

    @Override
    public PayStackPaymentInitiationResponseDto initiatePayment(StudentSchoolFeePaymentDto studentSchoolFeePaymentDto) {
        Validator validator;
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(studentSchoolFeePaymentDto);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(v -> stringBuilder.append(v.getMessage()).append("; "));
            throw new BadRequestException(stringBuilder.toString());
        }
//        Find the school fee with the id
        Optional<StudentSchoolFee> studentSchoolFeeOpt = studentSchoolFeeRepository.findByIdAndStudentIdNumber(
                studentSchoolFeePaymentDto.getFeeId(),
                studentSchoolFeePaymentDto.getStudentIdNo()
        );
        if (studentSchoolFeeOpt.isEmpty()) {
            throw new ResourceNotFoundException("School fee with the id "  + studentSchoolFeePaymentDto.getFeeId() + "for student with the student id number " + studentSchoolFeePaymentDto.getStudentIdNo() + " was not found");
        }

//        If the fee exists, confirm if it is still pending
        StudentSchoolFee studentSchoolFee = studentSchoolFeeOpt.get();
        if (studentSchoolFee.getPaymentStatus().equals(PaymentStatus.PAID)) {
            throw new BadRequestException("The fee has already been paid");
        }
        StudentSchoolFeeDtoPaystack studentSchoolFeeDto = studentSchoolFeeMapper.toStudentSchoolFeeDtoPaystack(studentSchoolFee);
        final double NAIRA_TO_KOBO_FACTOR = 100.00;
        StudentSchoolFeePaymentRequest studentSchoolFeePaymentRequest = new StudentSchoolFeePaymentRequest();
        studentSchoolFeePaymentRequest.setStudentIdNumber(studentSchoolFeePaymentDto.getStudentIdNo());
        studentSchoolFeePaymentRequest.setEmail(studentSchoolFee.getStudent().getGuardian().getEmail());
        studentSchoolFeePaymentRequest.setAmount(studentSchoolFee.getTotalDueAmount() * NAIRA_TO_KOBO_FACTOR);
        return paymentService.initiatePaystackPayment(studentSchoolFeePaymentRequest, studentSchoolFeeDto, PAYMENT_TYPE.SCHOOL_FEES);
    }
}
