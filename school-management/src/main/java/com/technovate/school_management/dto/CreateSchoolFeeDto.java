package com.technovate.school_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolFeeDto {
    @NotNull(message = "School class id is required")
    private Long schoolClassId;
    @NotNull(message = "Fee id is required")
    private Long feeId;
}
