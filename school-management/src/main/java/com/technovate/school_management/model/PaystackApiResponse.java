package com.technovate.school_management.model;

import com.technovate.school_management.dto.PayStackPaymentInitiationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaystackApiResponse {
    private boolean status;
    private String message;
    private PayStackPaymentInitiationResponseDto data;
}
