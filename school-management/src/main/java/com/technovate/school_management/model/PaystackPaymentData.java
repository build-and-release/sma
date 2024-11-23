package com.technovate.school_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackPaymentData<T> {
    private long id;
    private String domain;
    private String status;
    private String reference;
    private long amount;
    private String message;
    private String gatewayResponse;
    private ZonedDateTime paidAt;
    private T metadata;
}
