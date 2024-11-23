package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuardianDto {
    private Long id;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String nationality;
}
