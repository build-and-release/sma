package com.technovate.school_management.dto;

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
public class FeeDto {
    private Long id;
    private String description;
    private Long lateFee;
    private LocalDate dueDate;
    private List<FeeItemListDto> feeItems;
}
