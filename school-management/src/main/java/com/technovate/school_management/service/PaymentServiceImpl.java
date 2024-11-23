package com.technovate.school_management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.technovate.school_management.dto.PayStackPaymentInitiationResponseDto;
import com.technovate.school_management.dto.StudentSchoolFeeDtoPaystack;
import com.technovate.school_management.entity.StudentSchoolFee;
import com.technovate.school_management.entity.enums.PaymentStatus;
import com.technovate.school_management.model.PaystackApiResponse;
import com.technovate.school_management.model.PaystackConfig;
import com.technovate.school_management.model.PaystackPaymentData;
import com.technovate.school_management.model.PaystackPaymentWebHookRequest;
import com.technovate.school_management.model.enums.PAYMENT_TYPE;
import com.technovate.school_management.repository.StudentSchoolFeeRepository;
import com.technovate.school_management.service.contracts.PaymentService;
import com.technovate.school_management.service.contracts.StudentSchoolFeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaystackConfig paystackConfig;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;  // ObjectMapper for JSON deserialization
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final StudentSchoolFeeRepository studentSchoolFeeRepository;

    public Map<String, Object> convertObjectToMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    @Override
    public <T> PayStackPaymentInitiationResponseDto initiatePaystackPayment(Object paymentData, T metaData, PAYMENT_TYPE paymentType) {
        try {
            Map<String, Object> payload = convertObjectToMap(paymentData);
            payload.put("callback_url", paystackConfig.getCallBackUrl());
            Map<String, Object> paymentMetaData = convertObjectToMap(metaData);
            paymentMetaData.put("paymentType", paymentType);
            payload.put("metadata", paymentMetaData);
            // Send POST request and retrieve response as a String
            String response = webClient.post()
                    .uri(paystackConfig.getPaystackUrl())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + paystackConfig.getSecretKey())
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Deserialize the response string into PayStackPaymentInitiationResponseDto
            PaystackApiResponse paystackApiResponse = objectMapper.readValue(response, PaystackApiResponse.class);
            return paystackApiResponse.getData();

        } catch (WebClientResponseException ex) {
            // Handle WebClient-specific exceptions (HTTP errors, etc.)
            throw new RuntimeException("Error occurred during Paystack API call: " + ex.getMessage(), ex);
        } catch (Exception e) {
            // Handle JSON deserialization or other unexpected errors
            throw new RuntimeException("Failed to process Paystack payment initiation response: " + e.getMessage(), e);
        }
    }

    @Override
    public void processPayment(Object webhookCallData) {
        try {
            Map<String, Object> callData = convertObjectToMap(webhookCallData);
            Map<String, Object> data = convertObjectToMap(callData.get("data"));
            Map<String, Object> metaData = convertObjectToMap(data.get("metadata"));
            String paymentType = (String) metaData.get("paymentType");
            if (paymentType == null) {
                throw new RuntimeException("Invalid payment type");
            }
            PAYMENT_TYPE paymentTypeInfo = PAYMENT_TYPE.valueOf(paymentType.toUpperCase());
            if (paymentTypeInfo == PAYMENT_TYPE.SCHOOL_FEES) {
                PaystackPaymentWebHookRequest<StudentSchoolFeeDtoPaystack> schoolFeePaymentRequest = objectMapper
                        .convertValue(webhookCallData, new TypeReference<PaystackPaymentWebHookRequest<StudentSchoolFeeDtoPaystack>>() {
                        });
                this.processSchoolFeePayment(schoolFeePaymentRequest);
                return;
            }
            throw new Exception("Invalid payment type: " + paymentType);
        } catch (Exception ex) {
            logger.error("Attempt to process payment with this data: {} threw an an exception: {}", webhookCallData.toString(), ex.getMessage());
        }
    }

    private void processSchoolFeePayment(PaystackPaymentWebHookRequest<StudentSchoolFeeDtoPaystack> paystackPaymentWebHookRequest) {
        PaystackPaymentData<StudentSchoolFeeDtoPaystack> paystackPaymentData = paystackPaymentWebHookRequest.getData();
//
        StudentSchoolFeeDtoPaystack studentSchoolFeeDto =  paystackPaymentData.getMetadata();
//                Find the school fee with the id
        Optional<StudentSchoolFee> studentSchoolFeeOpt = studentSchoolFeeRepository.findByIdAndStudentIdNumber(
                studentSchoolFeeDto.getFeeId(),
                studentSchoolFeeDto.getStudentIdNumber()
        );
        if (studentSchoolFeeOpt.isEmpty()) {
            return;
        }
        StudentSchoolFee studentSchoolFee = studentSchoolFeeOpt.get();
        studentSchoolFee.setPaymentReference( paystackPaymentData.getReference());
        studentSchoolFee.setPayedOn( paystackPaymentData.getPaidAt().toLocalDateTime());
        studentSchoolFee.setPaymentStatus(PaymentStatus.PAID);
        studentSchoolFeeRepository.save(studentSchoolFee);
    }
}
