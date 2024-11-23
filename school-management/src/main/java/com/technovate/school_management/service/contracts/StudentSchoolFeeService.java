package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.*;
import com.technovate.school_management.model.PaystackPaymentWebHookRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentSchoolFeeService {
    void addSchoolFeeForStudent(Long studentId, Long schoolClassId);
    Page<StudentSchoolFeeDto> findAllByStudentIdNumber(FindByStudentIdNumberPaginatedDto findByStudentIdNumberPaginatedDto);
    List<StudentSchoolFeeDto> findAllByStudentIdNumberAndPaymentStatus(String studentIdNumber, String paymentStatus);
    PayStackPaymentInitiationResponseDto initiatePayment(StudentSchoolFeePaymentDto studentSchoolFeePaymentDto);
}
