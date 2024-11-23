package com.technovate.school_management.entity.enums;

public enum PaymentStatus {
    PAID("Paid"),
    PENDING("Pending");

    private String paymentStatus;

    private PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private String getPaymentStatus() {
        return this.paymentStatus;
    }
}
