package com.technovate.school_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeeDto {
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    @NotNull(message = "Late Fee is required")
    private double lateFee;
    @NotNull(message = "Fee items are required")
    private List<CreateFeeItemDto> feeItems;
}
