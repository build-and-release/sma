package com.technovate.school_management.dto;

import com.technovate.school_management.annotation.validation.ValidAlternatePhoneNumber;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGradeDto {
    @NotNull(message = "Student Score is required")
    private double studentScore;
    @NotNull(message =  "Class subject Id is required")
    private Long classSubjectId;
    @NotNull(message = "Student Id is required")
    private String studentIdNumber;
}
