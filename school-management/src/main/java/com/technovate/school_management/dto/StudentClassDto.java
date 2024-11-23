package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassDto {
    private Long id;
    private SchoolClassDto schoolClass;
    private StudentDto student;
    private boolean isCurrent;
}
