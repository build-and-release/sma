package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeListDto {
    private Long id;
    private String description;
    private Long lateFee;
    private LocalDate dueDate;
    private double total;
}
