package com.technovate.school_management.dto;

import com.technovate.school_management.annotation.validation.FutureDate;
import com.technovate.school_management.annotation.validation.ValidEndDate;
import com.technovate.school_management.annotation.validation.ValidTermName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidEndDate
public class CreateTermDto {
    @NotNull(message = "Name is required")
    @ValidTermName // Custom validation for term name
    private String name;

    @NotNull(message = "Start date is required")
    @FutureDate // Custom validation for future date
    private LocalDate startDate;
    private LocalDate endDate;
}
