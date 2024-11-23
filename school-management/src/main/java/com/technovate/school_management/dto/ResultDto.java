package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private StudentDto student;
    private StudentClassDto studentClass;
    private TermDto term;
}
