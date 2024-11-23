package com.technovate.school_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindByStudentIdNumberPaginatedDto {
    @NotNull(message = "Id number is required")
    @Size(min = 4, message = "Id number must be at least 4 characters long")
    private String idNumber;
    private Integer page = 0;
    private Integer size = 10;
}
