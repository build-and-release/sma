package com.technovate.school_management.controller;

import com.technovate.school_management.dto.*;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.service.contracts.SchoolFeeService;
import com.technovate.school_management.service.contracts.StudentSchoolFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/school-fee")
@RequiredArgsConstructor
public class SchoolFeeController {
    private final SchoolFeeService schoolFeeService;
    private final StudentSchoolFeeService studentSchoolFeeService;

    @PostMapping("")
    ResponseEntity<ApiResponseType<String>> createSchoolFee(@RequestBody CreateSchoolFeeDto createSchoolFeeDto) {
        schoolFeeService.CreateSchoolFee(createSchoolFeeDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse("School fee created successfully"));
    }

    @GetMapping("current-school-fee/{classId}")
    ResponseEntity<ApiResponseType<SchoolFeeListDto>> getCurrentSchoolFeeForClass(@PathVariable Long classId) {
        SchoolFeeListDto schoolFee = schoolFeeService.findSchoolClassFeeForCurrentTerm(classId);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(schoolFee));
    }

    @PostMapping("student")
    ResponseEntity<ApiResponseType<Page<StudentSchoolFeeDto>>> getStudentFees(@RequestBody FindByStudentIdNumberPaginatedDto findByStudentIdNumberPaginatedDto) {
        Page<StudentSchoolFeeDto> studentSchoolFeeDtos = studentSchoolFeeService.findAllByStudentIdNumber(findByStudentIdNumberPaginatedDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(studentSchoolFeeDtos));
    }

    @PostMapping("pay")
    ResponseEntity<ApiResponseType<PayStackPaymentInitiationResponseDto>> initializePayment(@RequestBody StudentSchoolFeePaymentDto studentSchoolFeePaymentDto) {
        PayStackPaymentInitiationResponseDto payStackPaymentInitiationResponseDto = studentSchoolFeeService.initiatePayment(
                studentSchoolFeePaymentDto
        );
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(payStackPaymentInitiationResponseDto));
    }
}
