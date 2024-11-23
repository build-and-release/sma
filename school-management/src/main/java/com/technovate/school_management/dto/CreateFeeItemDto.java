package com.technovate.school_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeeItemDto {
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;
}
