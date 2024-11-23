package com.technovate.school_management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaystackConfig {
    private String secretKey;
    private String publicKey;
    private String paystackUrl;
    private String callBackUrl;
}
