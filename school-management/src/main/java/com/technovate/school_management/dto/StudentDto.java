package com.technovate.school_management.dto;

import com.technovate.school_management.entity.SchoolClass;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private GuardianDto guardian;
    private String nationality;
    private String idNumber;
    private String passportUrl;
    private String guardianType;
}
