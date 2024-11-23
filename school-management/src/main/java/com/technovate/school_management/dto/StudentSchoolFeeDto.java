package com.technovate.school_management.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.technovate.school_management.configuration.SafeGenericDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSchoolFeeDto {
    private Long id;
    private String studentIdNumber;
    private double totalDueAmount;
    private String paymentStatus;
    private String description;
    private LocalDateTime payedOn;
    private SchoolClassDto schoolClass;
    private String term;
    private LocalDate dueDate;
}
