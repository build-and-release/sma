package com.technovate.school_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PaystackPaymentWebHookRequest<T> {
    private String event;
    private PaystackPaymentData<T> data;
}
