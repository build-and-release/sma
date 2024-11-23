package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.PayStackPaymentInitiationResponseDto;
import com.technovate.school_management.model.enums.PAYMENT_TYPE;

public interface PaymentService {
    <T> PayStackPaymentInitiationResponseDto initiatePaystackPayment(Object paymentData, T metaData, PAYMENT_TYPE paymentType);
    void processPayment(Object data);
}

