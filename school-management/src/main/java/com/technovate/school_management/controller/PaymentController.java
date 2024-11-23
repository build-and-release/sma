package com.technovate.school_management.controller;

import com.technovate.school_management.service.contracts.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("callback")
    void onPaymentSuccessCallback(@RequestBody Object paymentWebhookRequest) {
        paymentService.processPayment(paymentWebhookRequest);
    }
}
