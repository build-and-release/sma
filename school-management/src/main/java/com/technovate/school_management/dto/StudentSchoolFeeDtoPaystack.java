package com.technovate.school_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technovate.school_management.model.enums.PAYMENT_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentSchoolFeeDtoPaystack {
    private Long feeId;
    private String studentIdNumber;
    private double totalDueAmount;
    private PAYMENT_TYPE paymentType = PAYMENT_TYPE.SCHOOL_FEES;
}
