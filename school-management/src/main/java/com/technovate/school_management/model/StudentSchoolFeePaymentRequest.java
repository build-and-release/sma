package com.technovate.school_management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSchoolFeePaymentRequest {
    private String studentIdNumber;
    private String email;
    private double amount;
}
