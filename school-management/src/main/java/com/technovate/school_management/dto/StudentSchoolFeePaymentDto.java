package com.technovate.school_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSchoolFeePaymentDto {
    @NotNull(message = "Fee Id is required")
    private Long feeId;
    @NotNull(message = "Student Id number is required")
    @Size(min = 4, message = "Id number must be at least 4 characters long")
    private String studentIdNo;
}
