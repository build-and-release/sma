package com.technovate.school_management.model.enums;

public enum PAYMENT_TYPE {
    SCHOOL_FEES("School-Fees"),
    OTHER("Other");
    private final String paymentType;
    PAYMENT_TYPE(String paymentType) {
        this.paymentType = paymentType;
    }
    String getPaymentType() {
        return this.paymentType;
    }
}
