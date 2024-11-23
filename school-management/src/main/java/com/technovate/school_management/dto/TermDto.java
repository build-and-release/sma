package com.technovate.school_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TermDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
}
