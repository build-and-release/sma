package com.technovate.school_management.repository;

import com.technovate.school_management.entity.StudentSchoolFee;
import com.technovate.school_management.entity.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSchoolFeeRepository extends JpaRepository<StudentSchoolFee, Long> {
    Optional<StudentSchoolFee> findByStudentIdAndSchoolFeeTermId(Long studentId, Long termId);
    Page<StudentSchoolFee> findAllByStudentIdNumber(String studentIdNumber, Pageable pageable);
    List<StudentSchoolFee> findAllByStudentIdNumberAndPaymentStatus(String studentIdNumber, PaymentStatus paymentStatus);
    Optional<StudentSchoolFee> findByIdAndStudentIdNumber(Long id, String studentIdNumber);
}
