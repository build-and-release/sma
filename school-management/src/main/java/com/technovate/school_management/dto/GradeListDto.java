package com.technovate.school_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeListDto {
    private Long id;
    private double studentScore;
    private String StudentIdNumber;
    private Long resultId;
    private String term;
    private String schoolClass;
    private String subject;
    private String gradeType;
}
